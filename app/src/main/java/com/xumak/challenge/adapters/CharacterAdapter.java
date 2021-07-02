package com.xumak.challenge.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.xumak.challenge.DetailsActivity;
import com.xumak.challenge.R;
import com.xumak.challenge.actions.CharacterActions;
import com.xumak.challenge.models.Character;

import java.util.List;

public class CharacterAdapter extends BaseAdapter {
    private static List<Character> characters;
    private final LayoutInflater layoutInflater;
    private final Context context;
    private final CharacterActions characterActions;

    public CharacterAdapter(Context context, List<Character> characters, CharacterActions characterActions) {
        this.context = context;
        CharacterAdapter.characters = characters;
        this.layoutInflater = LayoutInflater.from(context);
        this.characterActions = characterActions;
    }

    @Override
    public int getCount() {
        return characters.size();
    }

    @Override
    public Character getItem(int position) {
        return characters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_character, null);
            holder = new ViewHolder();
            holder.txtVwCharacterName = convertView.findViewById(R.id.txtVwCharacterName);
            holder.btnCharacterDetails = convertView.findViewById(R.id.btnCharacterDetails);
            holder.btnCharacterLike = convertView.findViewById(R.id.btnCharacterLike);
            holder.btnCharacterUnlike = convertView.findViewById(R.id.btnCharacterUnlike);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Character character = getItem(position);
        holder.txtVwCharacterName.setText(character.getName());
        if (characters.get(position).getFavorite() > 0) {
            holder.btnCharacterLike.setVisibility(View.GONE);
            holder.btnCharacterUnlike.setVisibility(View.VISIBLE);
        } else {
            holder.btnCharacterLike.setVisibility(View.VISIBLE);
            holder.btnCharacterUnlike.setVisibility(View.GONE);
        }

        holder.btnCharacterLike.setOnClickListener(v -> {
            character.setFavorite(1);
            characterActions.update(character);
            holder.btnCharacterLike.setVisibility(View.GONE);
            holder.btnCharacterUnlike.setVisibility(View.VISIBLE);
        });

        holder.btnCharacterUnlike.setOnClickListener(v -> {
            character.setFavorite(0);
            characterActions.update(character);
            holder.btnCharacterLike.setVisibility(View.VISIBLE);
            holder.btnCharacterUnlike.setVisibility(View.GONE);
        });

        holder.btnCharacterDetails.setOnClickListener(v -> {
            Intent iDetailsActivity = new Intent(context, DetailsActivity.class);
            iDetailsActivity.putExtra("ID", character.getChar_id());
            context.startActivity(iDetailsActivity);
        });
        return convertView;
    }

    static class ViewHolder {
        TextView txtVwCharacterName;
        Button btnCharacterDetails, btnCharacterLike, btnCharacterUnlike;
    }
}
