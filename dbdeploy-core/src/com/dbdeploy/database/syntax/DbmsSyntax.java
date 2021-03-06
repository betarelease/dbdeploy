package com.dbdeploy.database.syntax;

import com.dbdeploy.scripts.ChangeScript;

public abstract class DbmsSyntax {

  public static DbmsSyntax createFor(String syntaxName) {
    return new DbmsSyntaxFactory().createDbmsSyntax(syntaxName);
  }

  public String generateScriptHeader() {
    return "";
  }

  public abstract String generateTimestamp();

  public abstract String generateUser();

  public String generateStatementDelimiter() {
    return ";";
  }

  public String generateCommit() {
    return "COMMIT" + generateStatementDelimiter();
  }

  public String generateBeginTransaction() {
    return "BEGIN TRANSACTION" + generateStatementDelimiter();
  }

  public String generateComment(String comment, ChangeScript changeScript) {
    return " ----- " + comment + " " + changeScript + " ----- ";
  }
}
