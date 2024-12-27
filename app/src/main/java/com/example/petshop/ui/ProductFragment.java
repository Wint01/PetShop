package com.example.petshop.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.petshop.MainActivity;
import com.example.petshop.R;

public class ProductFragment extends Fragment {

    private int productBgColor;
    private int productImageId;
    private String productTitle;
    private String productDescription;
    private String productPrice;
    private int productId;

    public static ProductFragment newInstance(int productBg, int productImage, String productTitle, String productDescription, String productPrice, int productId) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putInt("productBg", productBg);
        args.putInt("productImage", productImage);
        args.putString("productTitle", productTitle);
        args.putString("productDescription", productDescription);
        args.putString("productPrice", productPrice);
        args.putInt("productId", productId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productBgColor = getArguments().getInt("productBg");
            productImageId = getArguments().getInt("productImage");
            productTitle = getArguments().getString("productTitle");
            productDescription = getArguments().getString("productDescription");
            productPrice = getArguments().getString("productPrice");
            productId = getArguments().getInt("productId");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        ConstraintLayout productBg = view.findViewById(R.id.productPageBg);
        ImageView productImage = view.findViewById(R.id.productPageImage);
        TextView productTitleView = view.findViewById(R.id.productPageTitle);
        TextView productDescriptionView = view.findViewById(R.id.productPageDescription);
        TextView productPriceView = view.findViewById(R.id.productPagePrice);

        productBg.setBackgroundColor(productBgColor);
        productImage.setImageResource(productImageId);
        productTitleView.setText(productTitle);
        productDescriptionView.setText(productDescription);
        productPriceView.setText(productPrice);

        view.findViewById(R.id.addToCartButton).setOnClickListener(v -> addToCart());

        return view;
    }

    public void addToCart() {
        Log.d("ProductFragment", "addToCart: Item ID " + productId);
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).addCartItem(productId);
            Toast.makeText(getContext(), "Добавлено", Toast.LENGTH_LONG).show();
        }
    }
}