package com.example.todoapp;
//import the required Library

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

//Creating ListTodoFragment class that extends Fragment
public class ListTodoFragment extends Fragment {
    //Declare the required view components
    View rootView;
    RecyclerView rvListTodo;
    TodoViewModel viewModel;
    LinearLayoutManager manager;
    //initializing into TAG
    private static final String TAG = "TodoTest";

    /**
     * Initializes the activity.
     *
     * @param savedInstanceState The current state data.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Inflate fragment layout, selects recycle view,
     * set it's layout to linear using linear layout manager,
     * finally call the updateRV method and listen for ItemTouch using ITemTouchHelper.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_list_todo, container, false);
        //get view using it id
        rvListTodo = rootView.findViewById(R.id.list_todo_rv);
        //object of ViewModelProvider
        viewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        //object of LinearLayoutManager
        manager = new LinearLayoutManager(getActivity());
        //setOrientation to linearLayout with vertical orientation
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        //set the layout to view
        rvListTodo.setLayoutManager(manager);
        //updateRV function is called
        updateRV();
        //object ItemTouchHelper
        new ItemTouchHelper(
                //item that is swap to left side or right side
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    /**
                     * make a move in recyclerView
                     * @param recyclerView
                     * @param viewHolder
                     * @param target
                     * @return false
                     */
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    /**
                     * OnSwiped event is done in recyclerView
                     * @param viewHolder
                     * @param direction
                     */
                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        //get all the data from todoList
                        List<ETodo> todoList = viewModel.getAllTodos().getValue();
                        //create object of TodoAdapter
                        TodoAdaptor adaptor = new TodoAdaptor(todoList);
                        ETodo todo = adaptor.getTodoAt(viewHolder.getAdapterPosition());
                        //delete the view by it id
                        viewModel.deleteById(todo);
                        //toasts message that shows task deleted
                        Toast.makeText(getActivity(), "One todos deleted!", Toast.LENGTH_LONG).show();
                    }
                }).attachToRecyclerView(rvListTodo);

        //return rootView
        return rootView;
    }

    /**
     * Get all the todos using getAllTodos and observe them using observer.
     */
    void updateRV() {
        viewModel.getAllTodos().observe(getViewLifecycleOwner(), new Observer<List<ETodo>>() {
            @Override
            public void onChanged(List<ETodo> eTodos) {
                //SharedPreferences store and retrieve small amounts of primitive data as key/value pairs
                SharedPreferences preferences = getContext().getSharedPreferences("todo_pref", 0);
                int user_id = preferences.getInt("user_id", 0);
                //display in the console
                Log.d(TAG, "user_id: " + user_id + "size of etodo" + eTodos.size());
                //for loop get data less than eTodos size
                for (int i = 0; i < eTodos.size(); i++) {
                    //Log.d(TAG, "********user id of this todo is: " + eTodos.get(i).getUser_id() + " and title: " + eTodos.get(i).getTitle() + "***********");
                    //if set id is not equal to user_id
                    if (eTodos.get(i).getUser_id() != user_id) {
                        //Log.d(TAG, "user id of this todo is: " + eTodos.get(i).getUser_id() + " and title: " + eTodos.get(i).getTitle() + "user_id we are checking with : " + user_id );
                        //remove the given data
                        eTodos.remove(i);
                        //decreased the value of i
                        i--;
                    }
                }
                //crating the object of adaptor
                TodoAdaptor adaptor = new TodoAdaptor(eTodos);
                //setAdapter to view
                rvListTodo.setAdapter(adaptor);
            }
        });
    }

    //create class TodoHolder that inherit property of RecyclerView.ViewHolder
    private class TodoHolder extends RecyclerView.ViewHolder {
        //Declare the required view components
        TextView title, date, desc;
        CheckBox checkBox;
        TodoAdaptor adaptor;

        /**
         * @param inflater
         * @param parent
         */
        public TodoHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_todo, parent, false));
            //find view by id of required component
            title = itemView.findViewById(R.id.list_item_todo_tv_title);
            date = itemView.findViewById(R.id.list_item_todo_tv_text);
            desc = itemView.findViewById(R.id.list_item_todo_tv_desc);
            checkBox = itemView.findViewById(R.id.list_item_todo_cb_iscomplete);
            //object created of adaptor
            adaptor = new TodoAdaptor(viewModel.getAllTodos().getValue());
            // Create an anonymous implementation of OnClickListener for title
            title.setOnClickListener(new View.OnClickListener() {
                /**
                 * load the UpdateItem function
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    loadUpdateItem();
                }
            });
            // Create an anonymous implementation of OnClickListener for date
            date.setOnClickListener(new View.OnClickListener() {
                /**
                 * load the UpdateItem function
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    loadUpdateItem();
                }
            });
            // Create an anonymous implementation of OnClickListener for checkBox
            checkBox.setOnClickListener(new View.OnClickListener() {
                /**
                 * click event is done then
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    ETodo todo = adaptor.getTodoAt(getAdapterPosition());
                    todo.setCompleted(!todo.isCompleted());
                    viewModel.update(todo);
                }
            });
        }


        //function loadUpdateItem
        void loadUpdateItem() {
            //initializing the getAdapterPosition function in i variable
            int i = getAdapterPosition();
            ETodo todo = adaptor.getTodoAt(i);
            //creating the object of intent
            Intent intent = new Intent(getActivity(), EditActivity.class);
            //passing the value with id
            intent.putExtra("TodoId", todo.getId());
            //start activity
            startActivity(intent);
            //toast message with length long
            Toast.makeText(getContext(), "Update Item: " + todo.getId(), Toast.LENGTH_LONG).show();
        }

        /**
         * bind parameterized method
         *
         * @param todo
         */
        public void bind(ETodo todo) {
            //formatting the date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //get all the data from views
            title.setText(todo.getTitle());
            desc.setText(todo.getDescription());
            date.setText(sdf.format(todo.getTodoDate()));
            checkBox.setChecked(todo.isCompleted());
        }

    }

    //created TodoAdapter class that inherit the property of Recyclerview
    private class TodoAdaptor extends RecyclerView.Adapter<TodoHolder> {
        //declare a list of ETodo class
        List<ETodo> eTodoList;

        /**
         * initialized the todoList to eTodoList
         *
         * @param todoList
         */
        public TodoAdaptor(List<ETodo> todoList) {
            eTodoList = todoList;
        }

        /**
         * ViewGroup is the parent view that will hold your cell that you are about to create
         * viewType is useful if you have different types of cells in your list
         * This method returns the ViewHolder for our item, using the provided View.
         *
         * @param parent
         * @param viewType
         * @return TodoHolder function
         */
        @NonNull
        @Override
        public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TodoHolder(layoutInflater, parent);
        }

        /**
         * onBindViewHolder function is created with parameter
         *
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
            //getting the position of list item from ETodo class
            ETodo todo = eTodoList.get(position);
            //linear layout
            LinearLayout layout = (LinearLayout) ((ViewGroup) holder.title.getParent());
            //switch to set the background color of given component of app
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

        /**
         * function count the number of data from list
         *
         * @return size of eTodoList
         */
        @Override
        public int getItemCount() {
            return eTodoList.size();
        }

        /**
         * function return the position of list
         *
         * @param position
         * @return etodolist position
         */
        public ETodo getTodoAt(int position) {
            return eTodoList.get(position);
        }
    }
}
