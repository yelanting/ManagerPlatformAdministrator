/**
 * @author : 孙留平
 * @since : 2018年9月7日 下午2:19:36
 * @see:
 */
package com.administrator.platform.exception.base;

/**
 * @author : Administrator
 * @since : 2018年9月7日 下午2:19:36
 * @see :
 *      系统运行时异常基类 <br/>
 *      e.g.&nbsp;<br/>
 *      <br/>
 * 
 *      <code>@Component<br/>
 * @Exceptional(errorCode = "EE100-01") <br/>
 *                        public ExampleAppRuntimeException extends BaseAppRuntimeException{ }<br/>
 *                        <br/>
 *                        其子类如果使用<code>@Exceptional注解，而isLogging和handler没有指定，则默认为isLogging=true,handler=DefaultExceptionHandler.class
 * 
 */
public class BaseAppRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -6021077900819863433L;
    private String title;

    public BaseAppRuntimeException() {
        super();
    }

    public BaseAppRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseAppRuntimeException(String title, String message, Throwable cause) {
        super(message, cause);
        this.title = title;
    }

    public BaseAppRuntimeException(String message) {
        super(message);
    }

    public BaseAppRuntimeException(Throwable cause) {
        super(cause);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
