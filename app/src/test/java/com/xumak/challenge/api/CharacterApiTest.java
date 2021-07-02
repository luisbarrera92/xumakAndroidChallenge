package com.xumak.challenge.api;

import android.content.Context;

import com.xumak.challenge.R;
import com.xumak.challenge.models.Character;

import org.checkerframework.checker.units.qual.C;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;



@RunWith(MockitoJUnitRunner.class)
public class CharacterApiTest {

    private static final String FAKE_STRING = "https://www.breakingbadapi.com";

    @Mock
    Context mockContext;

    @Test
    public void testGetAll(){
        when(mockContext.getString(R.string.API_URL)).thenReturn(FAKE_STRING);
        ArrayList<String> occupations = new ArrayList<>();
        occupations.add("Driver");
        occupations.add("Policeman");
        Character character1 = new Character(1, 0, "Character1", "Dead", occupations);
        ArrayList<String> occupations2 = new ArrayList<>();
        occupations2.add("Doctor");
        occupations2.add("Architech");
        Character character2 = new Character(2, 0, "Character2", "Prision", occupations2);
        CharacterApi characterApi = new CharacterApi(mockContext);
        List<Character> list = new ArrayList<>();
        list.add(character1);
        list.add(character2);
        assertThat(list).containsNoneIn(characterApi.getAll());
    }
}