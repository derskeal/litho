/*
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.facebook.samples.litho.lithography;

import static com.facebook.litho.testing.assertj.ComponentConditions.textEquals;
import static com.facebook.litho.testing.assertj.LithoAssertions.assertThat;
import static com.facebook.litho.testing.assertj.SubComponentExtractor.subComponentWith;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assume.assumeThat;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.testing.ComponentsRule;
import com.facebook.litho.testing.InspectableComponent;
import com.facebook.litho.testing.SubComponent;
import com.facebook.litho.testing.assertj.SubComponentExtractor;
import com.facebook.litho.testing.testrunner.ComponentsTestRunner;
import com.facebook.litho.widget.Text;
import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(ComponentsTestRunner.class)
public class DecadeSeparatorSpecTest {
  @Rule public ComponentsRule mComponentsRule = new ComponentsRule();

  private Component<DecadeSeparator> mComponent;

  @Before
  public void setUp() {
    assumeThat("These tests can only be run in debug mode.",
        ComponentsConfiguration.IS_INTERNAL_BUILD, is(true));
    mComponent = DecadeSeparator.create(mComponentsRule.getContext()).decade(new Decade(2010)).build();
  }

  @Test
  public void testSubComponentsWithManualExtraction() {
    final ComponentContext c = mComponentsRule.getContext();
    assertThat(c, mComponent).extractingSubComponents(c).hasSize(3);
  }

  @Test
  public void testSubComponentByClass() {
    final ComponentContext c = mComponentsRule.getContext();
    assertThat(c, mComponent).hasSubComponents(SubComponent.of(Text.class));
  }

  @Test
  public void testSubComponentByClassWithExtraction() {
    final ComponentContext c = mComponentsRule.getContext();
    assertThat(c, mComponent).extracting(SubComponentExtractor.subComponents(c))
      .areExactly(1, new Condition<InspectableComponent>() {
        @Override
        public boolean matches(InspectableComponent value) {
          return value.getComponentClass() == Text.class;
        }
      });
  }

  @Test
  public void testSubComponentWithText() {
    final ComponentContext c = mComponentsRule.getContext();
    assertThat(c, mComponent)
        .has(subComponentWith(c, textEquals("2010")))
        // Silly thing to test for, but left here to demonstrate the API.
        .doesNotHave(subComponentWith(c, textEquals("2011")));
  }
}
