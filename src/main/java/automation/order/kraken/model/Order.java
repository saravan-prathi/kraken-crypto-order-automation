package automation.order.kraken.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

  @SerializedName("result")
  @Expose
  private Result result;
  /**
   *
   * @return
   * The result
   */
  public Result getResult() {
    return result;
  }
  /**
   *
   * @param result
   * The result
   */
  public void setResult(Result result) {
    this.result = result;
  }
}


