package com.dbdeploy;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnit44Runner;
import com.dbdeploy.scripts.ChangeScript;

@RunWith(MockitoJUnit44Runner.class)
public class ControllerTest {

  @Mock
  private AvailableChangeScriptsProvider availableChangeScriptsProvider;
  @Mock
  private AppliedChangesProvider appliedChangesProvider;
  @Mock
  private ChangeScriptApplier applier;
  @Mock
  private ChangeScriptApplier undoApplier;
  private Controller controller;
  private ChangeScript change1;
  private ChangeScript change2;
  private ChangeScript change3;
  private ChangeScript change4;
  List<Integer> applied = new ArrayList<Integer>();

  @Before
  public void setUp() {
    controller = new Controller(availableChangeScriptsProvider, appliedChangesProvider, applier, undoApplier);

    change1 = new ChangeScript(1);
    change2 = new ChangeScript(2);
    change3 = new ChangeScript(3);
    change4 = new ChangeScript(4);

    applied.add(0);

    when(availableChangeScriptsProvider.getAvailableChangeScripts()).thenReturn(
        Arrays.asList(change1, change2, change3, change4));
  }

  @Test
  public void shouldApplyChangeScriptsInOrder() throws Exception {
    when(appliedChangesProvider.getAppliedChanges()).thenReturn(applied);

    controller.processChangeScripts(0, Integer.MAX_VALUE);

    InOrder o = inOrder(applier);
    o.verify(applier).apply(change1);
    o.verify(applier).apply(change2);
    o.verify(applier).apply(change3);
  }

  @Test
  public void shouldApplyChangeScriptsForSpecifiedRange() throws Exception {
    when(appliedChangesProvider.getAppliedChanges()).thenReturn(applied);

    controller.processChangeScripts(Integer.valueOf(1), Integer.valueOf(3));

    InOrder o = inOrder(applier);
    o.verify(applier).apply(change2);
    o.verify(applier).apply(change3);
  }

  @Test
  public void shouldNotCrashWhenPassedANullUndoApplier() throws Exception {
    controller = new Controller(availableChangeScriptsProvider, appliedChangesProvider, applier, null);

    when(appliedChangesProvider.getAppliedChanges()).thenReturn(applied);

    controller.processChangeScripts(0, Integer.MAX_VALUE);
  }

  @Test
  public void shouldApplyUndoScriptsInReverseOrder() throws Exception {
    when(appliedChangesProvider.getAppliedChanges()).thenReturn(applied);

    controller.processChangeScripts(0, Integer.MAX_VALUE);

    InOrder o = inOrder(undoApplier);
    o.verify(undoApplier).apply(change3);
    o.verify(undoApplier).apply(change2);
    o.verify(undoApplier).apply(change1);
  }

  @Test
  public void shouldIgnoreChangesAlreadyAppliedToTheDatabase() throws Exception {
    when(appliedChangesProvider.getAppliedChanges()).thenReturn(Arrays.asList(1));

    controller.processChangeScripts(0, Integer.MAX_VALUE);

    InOrder o = inOrder(applier);
    o.verify(applier, never()).apply(change1);
    o.verify(applier).apply(change2);
    o.verify(applier).apply(change3);
  }

  @Test
  public void shouldNotApplyChangesGreaterThanTheMaxChangeToApply() throws Exception {
    when(appliedChangesProvider.getAppliedChanges()).thenReturn(applied);

    controller.processChangeScripts(0, 2);

    InOrder o = inOrder(applier);
    o.verify(applier).apply(change1);
    o.verify(applier).apply(change2);
    o.verify(applier, never()).apply(change3);
  }

  @Test
  public void shouldCallBeginAndEndOnTheApplier() throws Exception {
    when(appliedChangesProvider.getAppliedChanges()).thenReturn(applied);
    controller.processChangeScripts(0, Integer.MAX_VALUE);

    InOrder o = inOrder(applier);
    o.verify(applier).begin();
    o.verify(applier).apply(change1);
    o.verify(applier).apply(change2);
    o.verify(applier).apply(change3);
    o.verify(applier).end();
  }
}