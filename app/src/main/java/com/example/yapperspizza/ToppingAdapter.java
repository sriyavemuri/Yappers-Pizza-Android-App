package com.example.yapperspizza;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ToppingAdapter extends RecyclerView.Adapter<ToppingAdapter.ToppingViewHolder> {

    private List<Topping> toppings;
    private int selectedPosition = RecyclerView.NO_POSITION; // Tracks the selected position for visual feedback
    private OnToppingClickListener listener;

    /**
     * Interface for handling click events on a topping.
     */
    public interface OnToppingClickListener {
        void onToppingClick(int position);
    }

    /**
     * Constructor for ToppingAdapter.
     *
     * @param toppings the list of toppings
     * @param listener the click listener for topping selection
     */
    public ToppingAdapter(List<Topping> toppings, OnToppingClickListener listener) {
        this.toppings = toppings;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ToppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topping_item, parent, false);
        return new ToppingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToppingViewHolder holder, int position) {
        Topping topping = toppings.get(position);

        // Set topping details
        holder.toppingTextView.setText(topping.getName());
        holder.toppingImageView.setImageResource(topping.getImageResId());

        // Visual feedback for selection
        holder.itemView.setSelected(position == selectedPosition);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                // Update the selected position and refresh visual state
                int previousPosition = selectedPosition;
                selectedPosition = position;
                notifyItemChanged(previousPosition);
                notifyItemChanged(position);

                listener.onToppingClick(position); // Trigger the callback for the click event
            }
        });
    }

    @Override
    public int getItemCount() {
        return toppings.size();
    }

    /**
     * ViewHolder for each topping item.
     */
    public static class ToppingViewHolder extends RecyclerView.ViewHolder {
        TextView toppingTextView;
        ImageView toppingImageView;

        public ToppingViewHolder(@NonNull View itemView) {
            super(itemView);
            toppingTextView = itemView.findViewById(R.id.toppingTextView);
            toppingImageView = itemView.findViewById(R.id.toppingImageView);
        }
    }
}