package common.interaction;

import java.io.Serializable;

public class response implements Serializable {
    private responseCode responseCode;
    private String responseBody;

    public response(responseCode responseCode, String responseBody) {
        this.responseCode = responseCode;
        this.responseBody = responseBody;
    }

    /**
     * @return Response сode.
     */
    public responseCode getResponseCode() {
        return responseCode;
    }

    /**
     * @return Response body.
     */
    public String getResponseBody() {
        return responseBody;
    }

    @Override
    public String toString() {
        return "Response[" + responseCode + ", " + responseBody + "]";
    }
}
