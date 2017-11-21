/*
 * Copyright (C) 2017 Karumi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.karumi.screenshot;

import android.app.Activity;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.karumi.screenshot.di.MainComponent;
import com.karumi.screenshot.di.MainModule;
import com.karumi.screenshot.model.SuperHero;
import com.karumi.screenshot.model.SuperHeroesRepository;
import com.karumi.screenshot.ui.view.MainActivity;
import com.karumi.screenshot.ui.view.SuperHeroDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import it.cosenonjaviste.daggermock.DaggerMockRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class SuperHeroDetailActivityTest extends ScreenshotTest {

  @Rule public DaggerMockRule<MainComponent> daggerRule =
      new DaggerMockRule<>(MainComponent.class, new MainModule()).set(
          new DaggerMockRule.ComponentSetter<MainComponent>() {
            @Override public void setComponent(MainComponent component) {
              SuperHeroesApplication app =
                  (SuperHeroesApplication) InstrumentationRegistry.getInstrumentation()
                      .getTargetContext()
                      .getApplicationContext();
              app.setComponent(component);
            }
          });

  @Rule public IntentsTestRule<SuperHeroDetailActivity> activityRule =
      new IntentsTestRule<>(SuperHeroDetailActivity.class, true, false);

  @Mock SuperHeroesRepository repository;


  @Test
  public void showSomeSuperHero(){
    SuperHero superHero = givenSomeSuperHero();
    Activity activity = startActivity(superHero.getName());

    compareScreenshot(activity);
  }

  @Test
  public void showEmptySuperHero(){
    SuperHero superHero = givenSomeEmptySuperHero();
    Activity activity = startActivity(superHero.getName());
    compareScreenshot(activity);
  }

  @Test
  public void showAvenger(){
    SuperHero avenger = givenSomeAvenger();
    Activity activity = startActivity(avenger.getName());
    compareScreenshot(activity);
  }


  @Test
  public void showSuperHeroWithLongDescription(){
    SuperHero superHero = givenSomeSuperHeroWithLongDescription();
    Activity activity = startActivity(superHero.getName());
    compareScreenshot(activity);
  }

 /* @Test
  public void showSuperHeroWithLongDescriptionAfterScrollingToBottom(){

    //NO FUNCIONA PORQUE SALE EL FADING EDGE, PARA SOLUCIONARLO HABRÍA QUE MODIFICAR LA VIEW
    //DE PRODUCCION Y METERLE UNA EMPTY VIEW AL FINAL Y HACER UN SCROLLTO DE ESE ID DE VISTA

    SuperHero superHero = givenSomeSuperHeroWithLongDescription();

    Activity activity = startActivity(superHero.getName());


    onView(withChild(withId(R.id.recycler_view))).perform(swipeUp());

    compareScreenshot(activity);
  }
*/
  private SuperHero givenSomeAvenger() {
    SuperHero superHero = new SuperHero("AVENGER", null, true, "Avenger Description");

    when(repository.getByName(anyString())).thenReturn(superHero);
    return superHero;
  }

  private SuperHero givenSomeSuperHero() {
    SuperHero superHero = new SuperHero("SuperHero", null, false, "Super Hero Description");

    when(repository.getByName(anyString())).thenReturn(superHero);
    return superHero;
  }

  private SuperHero givenSomeSuperHeroWithLongDescription() {
    SuperHero superHero = new SuperHero("SuperHero", null, false, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt "
            + "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation "
            + "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in "
            + "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
            + "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt "
            + "mollit anim id est laborum."
    +"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt "
            + "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation "
            + "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in "
            + "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
            + "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt "
            + "mollit anim id est laborum.");

    when(repository.getByName(anyString())).thenReturn(superHero);
    return superHero;
  }

  private SuperHero givenSomeEmptySuperHero() {
    SuperHero superHero = new SuperHero("", null, false, "");

    when(repository.getByName(anyString())).thenReturn(superHero);
    return superHero;
  }

  private SuperHeroDetailActivity startActivity(String super_hero_name) {

    Intent i = new Intent();
    i.putExtra("super_hero_name_key", super_hero_name);
    return activityRule.launchActivity(i);
  }
}