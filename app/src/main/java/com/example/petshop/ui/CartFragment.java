package com.example.petshop.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.petshop.MainActivity;
import com.example.petshop.R;
import com.example.petshop.adapter.CartItemAdapter;
import com.example.petshop.model.CartItem;
import com.example.petshop.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CartFragment extends Fragment {

    private List<Product> fullProductList;
    private ListView cartItemsList;
    private CartItemAdapter cartItemAdapter;
    private Spinner deliverySpinner;
    private Spinner paymentSpinner;
    private TextView totalPriceText;
    private List<CartItem> cartItems = new ArrayList<>();
    private double totalPrice = 0.0;

    public static CartFragment newInstance(List<Product> products, Set<Integer> cartItems) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("products", new ArrayList<>(products));
        args.putIntegerArrayList("cartItems", new ArrayList<>(cartItems));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fullProductList = getArguments().getParcelableArrayList("products");
            ArrayList<Integer> cartItemIds = getArguments().getIntegerArrayList("cartItems");
            if (cartItemIds != null) {
                for (Integer itemId : cartItemIds) {
                    for (Product product : fullProductList) {
                        if (product.getId() == itemId) {
                            cartItems.add(new CartItem(product, 1));
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        cartItemsList = view.findViewById(R.id.cart_items_list);
        deliverySpinner = view.findViewById(R.id.delivery_spinner);
        paymentSpinner = view.findViewById(R.id.payment_spinner);
        totalPriceText = view.findViewById(R.id.total_price_text);

        // Инициализация адаптера
        cartItemAdapter = new CartItemAdapter(getContext(), cartItems, this::updateTotalPrice);
        cartItemsList.setAdapter(cartItemAdapter);

        // Настройка спиннеров
        setupSpinners();

        // Обновление итоговой цены
        updateTotalPrice();

        return view;
    }

    private void setupSpinners() {
        ArrayAdapter<CharSequence> deliveryAdapter = ArrayAdapter.createFromResource(
                getContext(), R.array.delivery_options, android.R.layout.simple_spinner_item
        );
        deliveryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deliverySpinner.setAdapter(deliveryAdapter);

        ArrayAdapter<CharSequence> paymentAdapter = ArrayAdapter.createFromResource(
                getContext(), R.array.payment_options, android.R.layout.simple_spinner_item
        );
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentSpinner.setAdapter(paymentAdapter);

        deliverySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDelivery = parent.getItemAtPosition(position).toString();
                Log.d("CartFragment", "Selected delivery: " + selectedDelivery);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        paymentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPayment = parent.getItemAtPosition(position).toString();
                Log.d("CartFragment", "Selected payment: " + selectedPayment);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void updateTotalPrice() {
        totalPrice = 0.0;
        for (CartItem item : cartItems) {
            totalPrice += item.getProduct().getPriceDouble() * item.getQuantity();
        }
        totalPriceText.setText("Итого: " + String.format("%.2f", totalPrice) + "$");
    }
}