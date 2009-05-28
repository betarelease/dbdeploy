package com.dbdeploy.appliers;

import java.io.PrintStream;
import com.dbdeploy.database.changelog.DatabaseSchemaVersionManager;
import com.dbdeploy.database.syntax.DbmsSyntax;
import com.dbdeploy.scripts.ChangeScript;

public class PrintStreamApplier extends AbstractChangeScriptApplier {

  private final PrintStream output;
  private final DbmsSyntax dbmsSyntax;
  private final DatabaseSchemaVersionManager schemaVersionManager;

  public PrintStreamApplier(ApplyMode mode, PrintStream output, DbmsSyntax dbmsSyntax,
      DatabaseSchemaVersionManager schemaVersionManager) {
    super(mode);
    this.output = output;
    this.dbmsSyntax = dbmsSyntax;
    this.schemaVersionManager = schemaVersionManager;
  }

  @Override
  public void begin() {
    output.println(dbmsSyntax.generateScriptHeader());
  }

  @Override
  protected void preChangeScriptApply(ChangeScript changeScript) {
    output.println();
    output.println(dbmsSyntax.generateComment("START CHANGE SCRIPT", changeScript));
    output.println();
  }

  @Override
  protected void beginTransaction() {
    output.println(dbmsSyntax.generateBeginTransaction());
  }

  @Override
  protected void applyChangeScriptContent(String scriptContent) {
    output.println();
    output.println(scriptContent);
    output.println();
  }

  @Override
  protected void insertToSchemaVersionTable(ChangeScript changeScript) {
    output.println(schemaVersionManager.getChangelogUpdateSql(changeScript) + dbmsSyntax.generateStatementDelimiter());
    output.println();
  }

  @Override
  protected void deleteFromSchemaVersionTable(ChangeScript changeScript) {
    output.println(schemaVersionManager.getChangelogDeleteSql(changeScript) + dbmsSyntax.generateStatementDelimiter());
    output.println();
  }

  @Override
  protected void commitTransaction() {
    output.println(dbmsSyntax.generateCommit());
  }

  @Override
  protected void postChangeScriptApply(ChangeScript changeScript) {
    output.println();
    output.println(dbmsSyntax.generateComment("END CHANGE SCRIPT", changeScript));
    output.println();
  }

  @Override
  public void end() {
    output.close();
  }

}
