package com.atilsamancioglu.kotlinadvanced.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.atilsamancioglu.kotlinadvanced.model.Country
import com.atilsamancioglu.kotlinadvanced.R
import com.atilsamancioglu.kotlinadvanced.databinding.ItemDogBinding
import kotlinx.android.synthetic.main.item_dog.view.*

class DogListAdapter(val dogList: ArrayList<Country>) : RecyclerView.Adapter<DogListAdapter.DogViewHolder>(),DogClickListener {


    class DogViewHolder(var view : ItemDogBinding) : RecyclerView.ViewHolder(view.root) {
        //ItemDogBinding is automatically created for us when we added <layout> tag in xml

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.item_dog, parent,false)
        val view = DataBindingUtil.inflate<ItemDogBinding>(inflater,R.layout.item_dog,parent,false)
        return DogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dogList.size
    }

    override fun onDogClicked(v: View) {
        super.onDogClicked(v)
        val uuid = v.dogId.text.toString().toInt()
        val action = ListFragmentDirections.actionDetailFragment()
        action.dogUuid = uuid
        Navigation.findNavController(v).navigate(action)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {

        holder.view.dog = dogList[position]
        //this is how we attach dog variable in the layout with the variable dogList.get(position)

        holder.view.listener = this
        //since we are implementing DogClickListener we can attach this to this


        /*
        holder.view.name.text = dogList.get(position).dogBreed
        holder.view.lifespan.text = dogList.get(position).lifeSpan

        holder.view.setOnClickListener{
            val action = ListFragmentDirections.actionDetailFragment()
            action.dogUuid = dogList.get(position).uuid
            Navigation.findNavController(it).navigate(action)
        }

        holder.view.imageView.loadImage(dogList.get(position).imageUrl!!, getProgressDrawable(holder.view.imageView.context))

         */
    }

    fun updateDogsList(newDogList: List<Country>) {
        dogList.clear()
        dogList.addAll(newDogList)
        notifyDataSetChanged()
    }
}