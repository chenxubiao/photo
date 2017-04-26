package cn.chenxubiao.common.utils;

/**
 * Created by chenxb on 17-4-2.
 */
public class WebUtil {

    public static String escapeHtml(String source) {
        return escapeHtml(source, false);
    }

    private static String escapeHtml(String source, boolean url) {
        if(source != null && source.length() != 0) {
            StringBuilder buff = new StringBuilder((int)((double)source.length() * 1.3D));

            for(int i = 0; i < source.length(); ++i) {
                char _char = source.charAt(i);
                switch(_char) {
                    case '"':
                        buff.append("&quot;");
                        break;
                    case '&':
                        if(url) {
                            buff.append(_char);
                        } else {
                            buff.append("&amp;");
                        }
                        break;
                    case '\'':
                        buff.append("&#39;");
                        break;
                    case '<':
                        buff.append("&lt;");
                        break;
                    case '>':
                        buff.append("&gt;");
                        break;
                    default:
                        buff.append(_char);
                }
            }

            return buff.toString();
        } else {
            return source;
        }
    }

    public static final String unescapeHtml(String source) {
        if(source != null && source.length() != 0) {
            StringBuilder buff = new StringBuilder(source.length());
            int skip = 0;

            boolean _continue;
            do {
                _continue = false;
                int i = source.indexOf("&", skip);
                if(i > -1) {
                    int j = source.indexOf(";", i);
                    if(j > i) {
                        String entity = (String)ConstStrings.HTML_ENTITY_MAP.get(source.substring(i, j + 1));
                        if(entity != null) {
                            buff.append(source.substring(skip, i));
                            buff.append(entity);
                        } else {
                            buff.append(source.substring(skip, j + 1));
                        }

                        skip = j + 1;
                        _continue = true;
                        continue;
                    }
                }

                buff.append(source.substring(skip));
            } while(_continue);

            return buff.toString();
        } else {
            return source;
        }
    }
}
