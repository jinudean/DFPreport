package com.example.dfpreport;

import java.util.List;

public interface ChecklistHandler {
    void showAddSubcategoryDialog(MainViewModel mainViewModel);
    void setAllCategories(List<Category> allCategories);
}
