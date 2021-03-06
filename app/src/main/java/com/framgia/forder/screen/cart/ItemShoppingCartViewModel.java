package com.framgia.forder.screen.cart;

import android.databinding.BaseObservable;
import com.framgia.forder.data.model.Cart;
import com.framgia.forder.data.model.CartItem;
import com.framgia.forder.utils.Utils;

/**
 * Created by tri on 19/04/2017.
 */

public class ItemShoppingCartViewModel extends BaseObservable {
    private Cart mCart;
    private CartItem mCartItem;
    private OrderItemListener mOrderItemListener;

    public ItemShoppingCartViewModel(Cart cart, OrderItemListener orderItemListener) {
        mCart = cart;
        mOrderItemListener = orderItemListener;
    }

    public ItemShoppingCartViewModel(CartItem cartItem, OrderItemListener orderItemListener) {
        mCartItem = cartItem;
        mOrderItemListener = orderItemListener;
    }

    public void orderOneShop() {
        if (mOrderItemListener == null) {
            return;
        }
        mOrderItemListener.onOrderOneShop(mCart);
    }

    public void upQuantity() {
        if (mOrderItemListener == null) {
            return;
        }
        mOrderItemListener.onUpQuantity(mCartItem);
    }

    public void downQuantity() {
        if (mOrderItemListener == null) {
            return;
        }
        mOrderItemListener.onDownQuantity(mCartItem);
    }

    public void deleteProduct() {
        if (mOrderItemListener == null) {
            return;
        }
        mOrderItemListener.onDeleteProduct(mCartItem);
    }

    public void clickIconWarning() {
        if (mOrderItemListener == null) {
            return;
        }
        mOrderItemListener.onClickIconWarning();
    }

    public void clickAddNotes() {
        if (mOrderItemListener == null) {
            return;
        }
        mOrderItemListener.onClickAddNotes(mCartItem);
    }

    public String getProductImage() {
        return mCartItem.getProductImage();
    }

    public String getProductName() {
        return mCartItem.getProductName();
    }

    public String getPrice() {
        return mCartItem.getFormatPrice();
    }

    public String getQuantity() {
        return Integer.toString(mCartItem.getQuantity());
    }

    public String getShopName() {
        return mCart.getShopName();
    }

    public String getTotal() {
        return mCart.getFormatTotal();
    }

    public String getNumberOfProduct() {
        return Integer.toString(mCart.getNumberOfProduct());
    }

    public String getOrderTime() {
        return mCartItem.getStartHour() + "-" + mCartItem.getEndHour();
    }

    public boolean isProductTimeOut() {
        return Utils.DateTimeUntils.isProductTimeOut(mCartItem.getStartHour(),
                mCartItem.getEndHour());
    }
}
