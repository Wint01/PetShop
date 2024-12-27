package com.example.petshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.petshop.R;
import com.example.petshop.model.CartItem;

import java.util.List;

public class CartItemAdapter extends ArrayAdapter<CartItem> {

    private final List<CartItem> cartItems;
    private final OnQuantityChangeListener quantityChangeListener;

    public CartItemAdapter(Context context, List<CartItem> cartItems, OnQuantityChangeListener quantityChangeListener) {
        super(context, 0, cartItems);
        this.cartItems = cartItems;
        this.quantityChangeListener = quantityChangeListener;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cart_item, parent, false);
        }

        CartItem cartItem = getItem(position);

        TextView cartItemTitle = convertView.findViewById(R.id.cart_item_title);
        TextView cartItemPrice = convertView.findViewById(R.id.cart_item_price);
        TextView cartItemQuantity = convertView.findViewById(R.id.cart_item_quantity);
        Button cartItemRemove = convertView.findViewById(R.id.cart_item_remove);
        Button cartItemAdd = convertView.findViewById(R.id.cart_item_add);

        if (cartItem != null) {
            cartItemTitle.setText(cartItem.getProduct().getTitle());
            cartItemPrice.setText(String.valueOf(cartItem.getProduct().getPrice()) + "$");
            cartItemQuantity.setText(String.valueOf(cartItem.getQuantity()));

            cartItemRemove.setOnClickListener(v -> {
                if (cartItem.getQuantity() > 1) {
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                    cartItemQuantity.setText(String.valueOf(cartItem.getQuantity()));
                    quantityChangeListener.onQuantityChanged();
                }
            });

            cartItemAdd.setOnClickListener(v -> {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartItemQuantity.setText(String.valueOf(cartItem.getQuantity()));
                quantityChangeListener.onQuantityChanged();
            });
        }

        return convertView;
    }

    public interface OnQuantityChangeListener {
        void onQuantityChanged();
    }
}