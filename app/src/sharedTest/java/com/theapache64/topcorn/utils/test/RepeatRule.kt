package com.theapache64.topcorn.utils.test

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RepeatRule : TestRule {

    private class RepeatStatement(private val statement: Statement, private val repeat: Int) :
        Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            for (i in 0 until repeat) {
                statement.evaluate()
            }
        }
    }

    override fun apply(statement: Statement, description: Description): Statement {
        var result = statement
        val repeat = description.getAnnotation(Repeat::class.java)
        if (repeat != null) {
            val times = repeat.value
            result = RepeatStatement(statement, times)
        }
        return result
    }
}