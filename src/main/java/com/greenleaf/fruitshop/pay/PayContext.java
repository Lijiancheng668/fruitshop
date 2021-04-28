package com.greenleaf.fruitshop.pay;

import com.greenleaf.fruitshop.model.entity.FruitInfo;
import com.greenleaf.fruitshop.model.entity.Orders;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PayContext {

    private Orders orders;

    private List<FruitInfo> bookInfos;

    private HttpServletResponse response;

    public List<FruitInfo> getBookInfos() {
        return bookInfos;
    }

    public void setBookInfos(List<FruitInfo> bookInfos) {
        this.bookInfos = bookInfos;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
}
