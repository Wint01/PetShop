package com.example.petshop.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petshop.R;
import com.example.petshop.adapter.CategoryAdapter;
import com.example.petshop.adapter.ProductAdapter;
import com.example.petshop.model.Category;
import com.example.petshop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView categoryRecycler;
    private RecyclerView productRecycler;
    private ProductAdapter productAdapter;
    private List<Product> fullProductList;
    private List<Product> productList;

    public static HomeFragment newInstance(List<Product> products) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("products", new ArrayList<>(products));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fullProductList = getArguments().getParcelableArrayList("products");
            if (fullProductList != null) {
                productList = new ArrayList<>(fullProductList);
            } else {
                productList = new ArrayList<>();
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        categoryRecycler = rootView.findViewById(R.id.categoryRecycler);
        productRecycler = rootView.findViewById(R.id.productRecycler);

        // Заглушки данных
        List<Category> categories = getCategories();

        // Установка адаптера категорий
        CategoryAdapter categoryAdapter = new CategoryAdapter(requireContext(), categories, new CategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick(int categoryId) {
                if (categoryId == 6) {
                    showAllProducts();
                } else {
                    filterProductsByCategory(categoryId);
                }
            }
        });
        categoryRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryRecycler.setAdapter(categoryAdapter);

        // Установка адаптера продуктов
        productAdapter = new ProductAdapter(requireContext(), productList);
        productRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        productRecycler.setAdapter(productAdapter);

        return rootView;
    }

    private List<Category> getCategories() {
        List<Category> categoriesList = new ArrayList<>();
        categoriesList.add(new Category(1, "Собаки"));
        categoriesList.add(new Category(2, "Кошки"));
        categoriesList.add(new Category(3, "Рыбы"));
        categoriesList.add(new Category(4, "Птицы"));
        categoriesList.add(new Category(5, "Грызуны"));
        categoriesList.add(new Category(6, "Все"));
        return categoriesList;
    }

    private void filterProductsByCategory(int categoryId) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : fullProductList) {
            if (product.getCategory() == categoryId) {
                filteredProducts.add(product);
            }
        }
        productList.clear();
        productList.addAll(filteredProducts);
        productAdapter.updateProducts(productList);
    }
    private void showAllProducts() {
        productList.clear();
        productList.addAll(fullProductList);
        productAdapter.updateProducts(productList);
    }
}