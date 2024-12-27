package com.example.petshop.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashSet;
import java.util.Set;

public class CartViewModel extends ViewModel {
    private MutableLiveData<Set<Integer>> items_id = new MutableLiveData<>(new HashSet<>());

    public LiveData<Set<Integer>> getItemsId() {
        return items_id;
    }

    public void addItem(int itemId) {
        Set<Integer> currentItems = items_id.getValue();
        if (currentItems != null) {
            currentItems.add(itemId);
            items_id.setValue(currentItems);
            Log.d("CartViewModel", "addItem: Item ID " + itemId + " added. Current items: " + currentItems);
            Log.d("CartViewModel", "addItem: LiveData value after set: " + items_id.getValue());
        }
    }

    public void removeItem(int itemId){
        Set<Integer> currentItems = items_id.getValue();
        if (currentItems != null) {
            currentItems.remove(itemId);
            items_id.setValue(currentItems);
            Log.d("CartViewModel", "removeItem: Item ID " + itemId + " removed. Current items: " + currentItems);
        }
    }
}