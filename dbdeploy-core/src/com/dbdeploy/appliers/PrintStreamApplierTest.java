package com.dbdeploy.appliers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.io.PrintStream;
import org.junit.Test;
import com.dbdeploy.database.changelog.DatabaseSchemaVersionManager;
import com.dbdeploy.database.syntax.DbmsSyntax;
import com.dbdeploy.scripts.ChangeScript;

public class PrintStreamApplierTest {

  @Test
  public void shouldDelegateCommentGenerationToSyntax() {
    ApplyMode mode = null;
    PrintStream output = mock(PrintStream.class);
    DbmsSyntax dbmsSyntax = mock(DbmsSyntax.class);
    DatabaseSchemaVersionManager schemaVersionManager = null;

    PrintStreamApplier printStreamApplier = new PrintStreamApplier(mode, output, dbmsSyntax, schemaVersionManager);
    ChangeScript changeScript = new ChangeScript(0);

    printStreamApplier.preChangeScriptApply(changeScript);
    verify(dbmsSyntax).generateComment("START CHANGE SCRIPT", changeScript);

    printStreamApplier.postChangeScriptApply(changeScript);
    verify(dbmsSyntax).generateComment("END CHANGE SCRIPT", changeScript);

  }
}
