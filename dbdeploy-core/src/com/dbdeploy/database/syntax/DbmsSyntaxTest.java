package com.dbdeploy.database.syntax;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.dbdeploy.scripts.ChangeScript;

public class DbmsSyntaxTest {

  @Test
  public void shouldGenerateCommentStringForMySQL() {
    MySQLDbmsSyntax mySqlDbmsSyntax = new MySQLDbmsSyntax();
    String comment = "comment";
    ChangeScript changeScript = new ChangeScript(0);
    String formattedComment = mySqlDbmsSyntax.generateComment(comment, changeScript);
    assertTrue(formattedComment.startsWith("#"));
    assertTrue(formattedComment.contains(comment));
    assertTrue(formattedComment.contains(changeScript.toString()));
  }

  @Test
  public void shouldGenerateStandardCommentString() {
    HsqlDbmsSyntax hsqlDbmsSyntax = new HsqlDbmsSyntax();
    String comment = "comment";
    ChangeScript changeScript = new ChangeScript(0);
    String formattedComment = hsqlDbmsSyntax.generateComment(comment, changeScript);
    assertTrue(formattedComment.contains(comment));
    assertTrue(formattedComment.contains(changeScript.toString()));
  }

}
