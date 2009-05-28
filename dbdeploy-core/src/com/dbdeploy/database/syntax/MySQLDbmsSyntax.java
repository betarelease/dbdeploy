package com.dbdeploy.database.syntax;

import com.dbdeploy.scripts.ChangeScript;

public class MySQLDbmsSyntax extends DbmsSyntax {

  public String generateTimestamp() {
    return "CURRENT_TIMESTAMP";
  }

  public String generateUser() {
    return "USER()";
  }

  @Override
  public String generateBeginTransaction() {
    return "START TRANSACTION" + generateStatementDelimiter();
  }

  public String generateComment(String comment, ChangeScript changeScript) {
    return "#" + " ----- " + comment + " " + changeScript + " ----- ";
  }
}
