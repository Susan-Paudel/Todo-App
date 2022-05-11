package com.example.todoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class ListTodoFragment extends Fragment {
    View rootView;
    RecyclerView rvListTodo;
    TodoViewModel viewModel;
    LinearLayoutManager manager;
    private static final String TAG = "TodoTest";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Inflate fragment layout, selects recycle view,
     * set it's layout to linear using linear layout manager,
     * finally call the updateRV method and listen for ItemTouch using ITemTouchHelper.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_list_todo, container, false);
        rvListTodo = rootView.findViewById(R.id.list_todo_rv);
        viewModel = new ViewModelProvider(this).get(TodoViewModel.class);

        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvListTodo.setLayoutManager(manager);
        updateRV();

        new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        List<ETodo> todoList = viewModel.getAllTodos().getValue();
                        TodoAdaptor adaptor = new TodoAdaptor(todoList);
                        ETodo todo = adaptor.getTodoAt(viewHolder.getAdapterPosition());
                        viewModel.deleteById(todo);
                    }
                }).attachToRecyclerView(rvListTodo);


        return  rootView;
    }

    /**
     * Get all the todos using getAllTodos and observe them using observer.
     */
    void updateRV(){
        viewModel.getAllTodos().observe(getViewLifecycleOwner(), new Observer<List<ETodo>>() {
            @Override
            public void onChanged(List<ETodo> eTodos) {
                SharedPreferences preferences= getContext().getSharedPreferences("todo_pref",0);
                int user_id  = preferences.getInt("user_id",0);

                Log.d(TAG, "user_id: " + user_id + "size of etodo" + eTodos.size());
                for (int i = 0; i< eTodos.size(); i++) {
//                    Log.d(TAG, "********user id of this todo is: " + eTodos.get(i).getUser_id() + " and title: " + eTodos.get(i).getTitle() + "***********");
                    if(eTodos.get(i).getUser_id() != user_id) {
//                        Log.d(TAG, "user id of this todo is: " + eTodos.get(i).getUser_id() + " and title: " + eTodos.get(i).getTitle() + "user_id we are checking with : " + user_id );
                        eTodos.remove(i);
                        i--;
                    }
                }
                TodoAdaptor adaptor = new TodoAdaptor(eTodos);
                rvListTodo.setAdapter(adaptor);
            }
        });
    }

    private class TodoHolder extends RecyclerView.ViewHolder {
        TextView title, date, desc;
        CheckBox checkBox;
        TodoAdaptor adaptor;
        public TodoHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_todo, parent, false));
            title = itemView.findViewById(R.id.list_item_todo_tv_title);
            date = itemView.findViewById(R.id.list_item_todo_tv_text);
            desc = itemView.findViewById(R.id.list_item_todo_tv_desc);
            checkBox = itemView.findViewById(R.id.list_item_todo_cb_iscomplete);

            adaptor = new TodoAdaptor(viewModel.getAllTodos().getValue());

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadUpdateItem();
                }
            });
            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadUpdateItem();
                }
            });

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ETodo todo = adaptor.getTodoAt(getAdapterPosition());
                    todo.setCompleted(!todo.isCompleted());
                    viewModel.update(todo);
                }
            });
        }




        void loadUpdateItem(){

            int i = getAdapterPosition();
            ETodo todo = adaptor.getTodoAt(i);
            Intent intent = new Intent(getActivity(), EditActivity.class);
            intent.putExtra("TodoId",todo.getId());
            startActivity(intent);
            Toast.makeText(getContext(),"Update Item: " + todo.getId(), Toast.LENGTH_LONG).show();
        }

        public void bind(ETodo todo){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            title.setText(todo.getTitle());
            desc.setText(todo.getDescription());
            date.setText(sdf.format(todo.getTodoDate()));
            checkBox.setChecked(todo.isCompleted());
        }

    }

    private class TodoAdaptor extends RecyclerView.Adapter<TodoHolder>{
        List<ETodo> eTodoList;
        public TodoAdaptor(List<ETodo> todoList)
        {
            eTodoList = todoList;
        }

        @NonNull
        @Override
        public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TodoHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
            ETodo todo = eTodoList.get(position);
            LinearLayout layout =(LinearLayout)((ViewGroup)holder.title.getParent());

            switch (todo.getPriority()) {
                case 1:
                    layout.setBackgroundColor(getResources().getColor(R.color.color_high));
                    break;
                case 2:
                    layout.setBackgroundColor(getResources().getColor(R.color.color_medium));
                    break;
                case 3:
                    layout.setBackgroundColor(getResources().getColor(R.color.color_low));
                    break;
            }
            holder.bind(todo);
        }

        @Override
        public int getItemCount() {
            return eTodoList.size();
        }

        public ETodo getTodoAt(int position){
            return eTodoList.get(position);
        }
    }
}
