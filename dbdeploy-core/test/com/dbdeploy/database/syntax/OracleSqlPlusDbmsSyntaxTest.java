package com.dbdeploy.database.syntax;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class OracleSqlPlusDbmsSyntaxTest{

    @Test
    public void shouldComplyWithImplicitTransactionsForOracle(){
        DbmsSyntax oracleSqlPlus = DbmsSyntax.createFor("ora-sqlplus");
        assertEquals("", oracleSqlPlus.generateBeginTransaction());
    }
}