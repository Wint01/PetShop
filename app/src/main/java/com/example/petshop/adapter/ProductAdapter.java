package com.example.petshop.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petshop.R;
import com.example.petshop.model.Product;
import com.example.petshop.ui.ProductFragment;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> products;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = new ArrayList<>(products); // Используем копию списка
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productItems = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ProductAdapter.ProductViewHolder(productItems);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = products.get(position);

        holder.productBg.setBackgroundColor(Color.parseColor(product.getColor()));
        int imageId = context.getResources().getIdentifier(product.getImage(), "drawable", context.getPackageName());
        holder.productImage.setImageResource(imageId);
        holder.productTitle.setText(product.getTitle());
        holder.productPrice.setText(product.getPrice());

        holder.itemView.setOnClickListener(v -> {
            ProductFragment productFragment = ProductFragment.newInstance(
                    Color.parseColor(product.getColor()),
                    imageId,
                    product.getTitle(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getId()
            );

            FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, productFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    // Добавляем метод для обновления списка продуктов
    public void updateProducts(List<Product> newProducts) {
        this.products.clear();
        this.products.addAll(newProducts);
        notifyDataSetChanged(); // Уведомляем адаптер об изменении данных
    }

    public static final class ProductViewHolder extends RecyclerView.ViewHolder {
        LinearLayout productBg;
        ImageView productImage;
        TextView productTitle;
        TextView productPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productBg = itemView.findViewById(R.id.productBg);
            productImage = itemView.findViewById(R.id.productImage);
            productTitle = itemView.findViewById(R.id.productTitle);
            productPrice = itemView.findViewById(R.id.productPrice);
        }
    }
}