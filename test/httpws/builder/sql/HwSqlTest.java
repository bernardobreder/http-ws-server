package httpws.builder.sql;

import org.junit.Assert;
import org.junit.Test;

public class HwSqlTest extends HwSql {
	
	@Test
	public void test() {
		eq("select 1 from dual", $select($val("1")).$from("dual").$close());
		eq("select a.id from a", $select($col("a.id")).$from("a").$close());
		eq("select a.id, b.id " + "from a, b", $select($col("a.id"), $col("b.id")).$from("a").$from("b").$close());
		eq("select a.id, b.id " + "from a " + "join b on a.id = b.id", $select($col("a.id"), $col("b.id")).$from("a").$join("b", $eq($col("a.id"), $col("b.id"))).$close());
		eq("select a.id, b.id " + "from a " + "join b on a.id = b.id and a.id = b.id", $select($col("a.id"), $col("b.id")).$from("a").$join("b", $and($eq($col("a.id"), $col("b.id")), $eq($col("a.id"),
			$col("b.id")))).$close());
		eq("select a.id, b.id " + "from a " + "join b on a.id = b.id and a.id = b.id", $select($col("a.id"), $col("b.id")).$from("a").$join("b", $col("a.id").$eq($col("b.id")).$and($col("a.id").$eq(
			$col("b.id")))).$close());
		eq("select a.id from a where a.id = 1", $select($col("a.id")).$from("a").$where($col("a.id").$eq($val(1))).$close());
		eq("select a.id from a group by a.id", $select($col("a.id")).$from("a").$group($col("a.id").$eq($val(1))).$close());
		eq("select a.id from a order by a.id", $select($col("a.id")).$from("a").$order($col("a.id")).$close());
		eq("select a.id, b.id " + "from a " + "join b on a.id = b.id " + "where a.id in (1,2,3) " + "group by a.id, b.id " + "order by a.id, b.id", $select($col("a.id"), $col("b.id")).$from("a")
			.$join("b", $col("a.id").$eq($col("b.id"))).$where($col("a.id").$in($val(1), $val(2), $val(3))).$group($col("a.id"), $col("b.id")).$order($col("a.id"), $col("b.id")).$close());
	}
	
	private void eq(String expected, HwSql sql) {
		Assert.assertEquals(expected, sql.$eof().toString());
	}
	
}
