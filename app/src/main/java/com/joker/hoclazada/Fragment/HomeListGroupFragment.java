package com.joker.hoclazada.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joker.hoclazada.R;
import com.zaihuishou.expandablerecycleradapter.adapter.BaseExpandableAdapter;
import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractAdapterItem;

import java.util.ArrayList;
import java.util.List;

import Model.Company;
import Model.CompanyItem;
import Model.Department;
import Model.DepartmentItem;
import Model.Employee;
import Model.EmployeeItem;
import adapter.AdapterListTopGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeListGroupFragment extends Fragment {
    private RecyclerView lvListTopGroup;
    private List<String> list;
    AdapterListTopGroup adapterListTopGroup;
    private final int ITEM_TYPE_COMPANY = 1;
    private final int ITEM_TYPE_DEPARTMENT = 2;
    private final int ITEM_TYPE_EMPLOYEE = 3;
    private RecyclerView mRecyclerView;
    private BaseExpandableAdapter mBaseExpandableAdapter;
    private List mCompanylist;
    private boolean hasAdd = false;

    public HomeListGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_home_list_group, container, false);
        lvListTopGroup = (RecyclerView) view.findViewById(R.id.lvListTopGroup);
        addGroup(view);
        addEvent();
        return view;
    }
    private void addGroup(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rcv);
        initData();
        mBaseExpandableAdapter = new BaseExpandableAdapter(mCompanylist) {
            @NonNull
            @Override
            public AbstractAdapterItem<Object> getItemView(Object type) {
                int itemType = (int) type;
                switch (itemType) {
                    case ITEM_TYPE_COMPANY:
                        return new CompanyItem();
                    case ITEM_TYPE_DEPARTMENT:
                        return new DepartmentItem();
                    case ITEM_TYPE_EMPLOYEE:
                        return new EmployeeItem();
                }
                return null;
            }

            @Override
            public Object getItemViewType(Object t) {
                if (t instanceof Company) {
                    return ITEM_TYPE_COMPANY;
                } else if (t instanceof Department)
                    return ITEM_TYPE_DEPARTMENT;
                else if (t instanceof Employee)
                    return ITEM_TYPE_EMPLOYEE;
                return -1;
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        /**
         * GridLayoutManager
         */
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setAdapter(mBaseExpandableAdapter);
        /**
         * set ExpandCollapseListener
         */
        mBaseExpandableAdapter.setExpandCollapseListener(new BaseExpandableAdapter.ExpandCollapseListener() {
            @Override
            public void onListItemExpanded(int position) {

//                BottomDialog dialog = BottomDialog.newInstance("title",new String[]{"item1","item2"});
//                dialog.show(getChildFragmentManager(),"dialog");
//                //add item click listener
//                dialog.setListener(new BottomDialog.OnClickListener() {
//                    @Override
//                    public void click(int position) {
//                        Toast.makeText(getContext(), "" + position, Toast.LENGTH_LONG).show();
//                    }
//                });

            }

            @Override
            public void onListItemCollapsed(int position) {

            }
        });
    }

    private void initData() {
        mCompanylist = new ArrayList<>();
        mCompanylist.add(createCompany("Bộ môn Tin", false));
        mCompanylist.add(createCompany("Bộ môn Toán", false));
        mCompanylist.add(createCompany("Bộ môn Anh", false));
        mCompanylist.add(createCompany("Bộ môn Thể chất", false));
        mCompanylist.add(createCompany("Group K26", false));
        mCompanylist.add(createCompany("Group K27", false));
        mCompanylist.add(createCompany("Group K28", false));
        mCompanylist.add(createCompany("Group K29", false));
        mCompanylist.add(createCompany("Group K30", false));
    }
    @NonNull
    private Company createCompany(String companyName, boolean isExpandDefault) {
        Company firstCompany = new Company();
        firstCompany.name = companyName;
        List<Department> departments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Department department = new Department();
            department.name = "Môn " + i;
            departments.add(department);
        }
        firstCompany.mDepartments = departments;
        firstCompany.mExpanded = isExpandDefault;
        return firstCompany;
    }
    private void addEvent() {
        list = new ArrayList<>();
        for (int i = 0; i< 20;i++){
            String a = i+ "hii";
            list.add(a);
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        adapterListTopGroup = new AdapterListTopGroup(getActivity(), list);

        lvListTopGroup.setLayoutManager(layoutManager);
        lvListTopGroup.setAdapter(adapterListTopGroup);
        lvListTopGroup.setNestedScrollingEnabled(false);
        adapterListTopGroup.notifyDataSetChanged();
    }

}
