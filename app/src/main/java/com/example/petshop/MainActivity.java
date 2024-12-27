package com.example.petshop;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.petshop.model.Product;
import com.example.petshop.ui.CartFragment;
import com.example.petshop.ui.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private List<Product> fullProductList = new ArrayList<>();
    private Set<Integer> cartItems = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fullProductList = getProducts();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                loadFragment(HomeFragment.newInstance(fullProductList));
                return true;
            } else if (itemId == R.id.nav_cart) {
                loadFragment(CartFragment.newInstance(fullProductList, getCartItems()));
                return true;
            }
            return false;
        });

        // Загрузка HomeFragment по умолчанию
        loadFragment(HomeFragment.newInstance(fullProductList));
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    private List<Product> getProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1, "сухой корм для собак", "sfdsdffsd", "#FFFFFFFF","325₽","logo",1));
        productList.add(new Product(2, "жидкий корм для собак", "sfdsdffsd", "#FFFFFFFF","400₽","logo",1));
        productList.add(new Product(3, "сухой корм для кошек", "sfdsdffsd", "#FFFFFFFF","375₽","logo",2));
        productList.add(new Product(4, "жидкий корм для кошек", "sfdsdffsd", "#FFFFFFFF","300₽","logo",2));
        productList.add(new Product(5, "жидкий корм для рыб", "sfdsdffsd", "#FFFFFFFF","150₽","logo",3));
        productList.add(new Product(6, "жидкий корм для рыб", "sfdsdffsd", "#FFFFFFFF","180₽","logo",3));
        productList.add(new Product(7, "жидкий корм для птиц", "sfdsdffsd", "#FFFFFFFF","300₽","logo",4));
        productList.add(new Product(8, "жидкий корм для птиц", "sfdsdffsd", "#FFFFFFFF","200₽","logo",4));
        productList.add(new Product(9, "жидкий корм для грызунов", "sfdsdffsd", "#FFFFFFFF","250₽","logo",5));
        productList.add(new Product(10, "жидкий корм для грызунов", "sfdsdffsd", "#FFFFFFFF","350₽","logo",5));
        return productList;
    }

    public void addCartItem(int itemId) {
        cartItems.add(itemId);
    }

    public Set<Integer> getCartItems() {
        return cartItems;
    }
}