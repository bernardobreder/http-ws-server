package httpws.builder.js;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HwJavascriptTest extends HwJavascript {
	
	@Test
	public void testAssignString() {
		$var("a").$assign($val("abc"));
		eq("a = \"abc\";");
	}
	
	@Test
	public void testAssignNumber() {
		$var("a").$assign($val(2));
		eq("a = 2;");
	}
	
	@Test
	public void testAssignTrue() {
		$var("a").$assign($val(true));
		eq("a = true;");
	}
	
	@Test
	public void testAssignFalse() {
		$var("a").$assign($val(false));
		eq("a = false;");
	}
	
	@Test
	public void testAssignThis() {
		$var("a").$assign($this());
		eq("a = this;");
	}
	
	@Test
	public void testAssignVar() {
		$var("a").$assign($var("b"));
		eq("a = b;");
	}
	
	@Test
	public void testAssign() {
		$var("a").$assign($val(1));
		$var("b").$assign($val(2));
		eq("a = 1;\n" + "b = 2;");
	}
	
	@Test
	public void testDefVar() {
		$def("a", $val(1));
		eq("var a = 1;");
	}
	
	@Test
	public void testIf() {
		$if($var("a").$eq($val(1)), () -> {
			$def("a", $val(1));
		});
		eq("if (a == 1) {\n" + "\tvar a = 1;\n" + "}");
	}
	
	@Test
	public void testIfElse() {
		$if($var("a").$eq($val(1)), () -> {
			$def("a", $val(1));
		}, () -> {
			$def("b", $val(2));
		});
		eq("if (a == 1) {\n" + "\tvar a = 1;\n" + "} else {\n" + "\tvar b = 2;\n" + "}");
	}
	
	@Test
	public void testIfElseIf() {
		$if($var("a").$eq($val(1)), () -> {
			$def("a", $val(1));
		}, null, $elseif($var("b").$eq($val(2)), () -> {
			$def("b", $val(2));
		}));
		eq("if (a == 1) {\n" + "\tvar a = 1;\n" + "} else if (b == 2) {\n" + "\tvar b = 2;\n" + "}");
	}
	
	@Test
	public void testIfElseIfElse() {
		$if($var("a").$eq($val(1)), () -> {
			$def("a", $val(1));
		}, () -> {
			$def("c", $val(3));
		}, $elseif($var("b").$eq($val(2)), () -> {
			$def("b", $val(2));
		}));
		eq("if (a == 1) {\n" + "\tvar a = 1;\n" + "} else if (b == 2) {\n" + "\tvar b = 2;\n" + "} else {\n" + "\tvar c = 3;\n" + "}");
	}
	
	@Test
	public void testIfElseIfElseIfElse() {
		$if($var("a").$eq($val(1)), () -> {
			$def("a", $val(1));
		}, () -> {
			$def("d", $val(4));
		}, $elseif($var("b").$eq($val(2)), () -> {
			$def("b", $val(2));
		}), $elseif($var("c").$eq($val(3)), () -> {
			$def("c", $val(3));
		}));
		eq("if (a == 1) {\n" + "\tvar a = 1;\n" + "} else if (b == 2) {\n" + "\tvar b = 2;\n" + "} else if (c == 3) {\n" + "\tvar c = 3;\n" + "} else {\n" + "\tvar d = 4;\n" + "}");
	}
	
	@Test
	public void testWhile() {
		$while($var("a").$eq($val(1)), () -> {
			$def("a", $val(1));
		});
		eq("while (a == 1) {\n" + "\tvar a = 1;\n" + "}");
	}
	
	@Test
	public void testReturn() {
		$return($var("a"));
		eq("return a;");
	}
	
	@Test
	public void testRepeat() {
		$repeat($var("a").$eq($val(1)), () -> {
			$def("a", $val(1));
		});
		eq("repeat (a == 1) {\n" + "\tvar a = 1;\n" + "}");
	}
	
	@Test
	public void testSwitch() {
		$switch($id("a"), null, $case($val(1), () -> {
			$def("a", $val(1));
		}));
		eq("switch (a) {\n" + "\tcase 1 : {\n" + "\t\tvar a = 1;\n" + "\t}\n" + "}");
	}
	
	@Test
	public void testSwitchDefault() {
		$switch($id("a"), () -> {
			$def("a", $val(1));
		});
		eq("switch (a) {\n" + "\tdefault : {\n" + "\t\tvar a = 1;\n" + "\t}\n" + "}");
	}
	
	@Test
	public void testBreak() {
		$break();
		eq("break;");
	}
	
	@Test
	public void testContinue() {
		$continue();
		eq("continue;");
	}
	
	@Test
	public void testSum() {
		$exec($var("a").$sum($val(1)));
		eq("a + 1;");
	}
	
	@Test
	public void testSub() {
		$exec($var("a").$sub($val(1)));
		eq("a - 1;");
	}
	
	@Test
	public void testMul() {
		$exec($var("a").$mul($val(1)));
		eq("a * 1;");
	}
	
	@Test
	public void testDiv() {
		$exec($var("a").$div($val(1)));
		eq("a / 1;");
	}
	
	@Test
	public void testMod() {
		$exec($var("a").$mod($val(1)));
		eq("a % 1;");
	}
	
	@Test
	public void testEq() {
		$exec($var("a").$eq($val(1)));
		eq("a == 1;");
	}
	
	@Test
	public void testNeq() {
		$exec($var("a").$neq($val(1)));
		eq("a != 1;");
	}
	
	@Test
	public void testLt() {
		$exec($var("a").$lt($val(1)));
		eq("a < 1;");
	}
	
	@Test
	public void testLe() {
		$exec($var("a").$le($val(1)));
		eq("a <= 1;");
	}
	
	@Test
	public void testGt() {
		$exec($var("a").$gt($val(1)));
		eq("a > 1;");
	}
	
	@Test
	public void testGe() {
		$exec($var("a").$ge($val(1)));
		eq("a >= 1;");
	}
	
	@Test
	public void testLeftShift() {
		$exec($var("a").$lshift($val(1)));
		eq("a << 1;");
	}
	
	@Test
	public void testRightShift() {
		$exec($var("a").$rshift($val(1)));
		eq("a >> 1;");
	}
	
	@Test
	public void testAnd() {
		$exec($var("a").$and($val(1)));
		eq("a && 1;");
	}
	
	@Test
	public void testOr() {
		$exec($var("a").$or($val(1)));
		eq("a || 1;");
	}
	
	@Test
	public void testAndBit() {
		$exec($var("a").$andbit($val(1)));
		eq("a & 1;");
	}
	
	@Test
	public void testOrBit() {
		$exec($var("a").$orbit($val(1)));
		eq("a | 1;");
	}
	
	@Test
	public void testCloseVal() {
		$exec($cval($val(1)));
		eq("(1);");
	}
	
	@Test
	public void testIncDec() {
		eq("a++;", $exec($var("a").$inc()));
		eq("a--;", $exec($var("a").$dec()));
		eq("++a;", $exec($var("a").$preinc()));
		eq("--a;", $exec($var("a").$predec()));
		eq("a += 2;", $var("a").$assignsum($val(2)));
		eq("a -= 2;", $var("a").$assignsub($val(2)));
		eq("a *= 2;", $var("a").$assignmul($val(2)));
		eq("a /= 2;", $var("a").$assigndiv($val(2)));
		eq("a %= 2;", $var("a").$assignmod($val(2)));
	}
	
	@Test
	public void testGetArray() {
		eq("a[2];", $exec($var("a").$array($val(2))));
	}
	
	@Test
	public void testCall() {
		eq("a.b(1);", $exec($var("a").$get("b").$call($val(1))));
		eq("a.b(1, 2);", $exec($var("a").$get("b").$call($val(1), $val(2))));
	}
	
	@Test
	public void testNew() {
		eq("new a();", $exec($new("a")));
		eq("new a(1);", $exec($new("a", $val(1))));
		eq("new a(1, 2);", $exec($new("a", $val(1), $val(2))));
	}
	
	@Test
	public void testGlobal() {
		eq("document;", $exec($document()));
		eq("window;", $exec($window()));
	}
	
	@Test
	public void testNewArray() {
		eq("[];", $exec($newarray()));
		eq("[1];", $exec($newarray($val(1))));
		eq("[1, 2];", $exec($newarray($val(1), $val(2))));
	}
	
	@Test
	public void testNewMap() {
		eq("{};", $exec($newmap()));
	}
	
	@Test
	public void testSpace() {
		eq("function () {\n" + "\ta(function () {\n" + "\t\tvar b = 1;\n" + "\t});\n" + "};", $exec($func(() -> {
			$exec($var("a").$call($func(() -> {
				$def("b", $val(1));
			})));
		})));
		eq("function () {\n" + "\tnew a(1, function () {\n" + "\t\tvar b = 1;\n" + "\t});\n" + "};", $exec($func(() -> {
			$exec($new("a", $val(1), $func(() -> {
				$def("b", $val(1));
			})));
		})));
	}
	
	@Test
	public void testFunction() {
		eq("function () {\n\n};", $exec($func(() -> {
		})));
	}
	
	@Test
	public void testDelete() {
		eq("delete a;", $delete($var("a")));
	}
	
	@Test
	public void testClass() {
		eq("var a = function (b, c) {\n\n}", $class("a", () -> {
		}, "b", "c"));
		eq("a.prototype.b = function (c, d) {\n\n}", $method("a", "b", () -> {
		}, "c", "d"));
	}
	
	@Test
	public void testFor() {
		eq("for (;;) {\n\n}", $for(null, null, null, () -> {
		}));
		eq("for (var a = 1;;) {\n\n}", $for($defv("a", $val(1)), null, null, () -> {
		}));
		eq("for (;a == 1;) {\n\n}", $for(null, $var("a").$eq($val(1)), null, () -> {
		}));
		eq("for (;;a++) {\n\n}", $for(null, null, $var("a").$inc(), () -> {
		}));
		eq("for (var a = 1;a == 1;a++) {\n\tvar b = 2;\n}", $for($defv("a", $val(1)), $var("a").$eq($val(1)), $var("a").$inc(), () -> {
			$def("b", $val(2));
		}));
	}
	
	@Test
	public void testExpr() {
		eq("a.b.c;", $exec($("a", "b", "c")));
		eq("a.b.c(1, \"2\");", $exec($("a", "b", "c").$call(1, "2")));
		eq("a.b.c(1, \"2\");", $exec($("a", "b", "c", $call(1, "2"))));
		eq("a.b.c[1];", $exec($("a", "b", "c", $array(1))));
	}
	
	@Test
	public void testExprSimple() {
		assertEquals("a.b.c;", new HwJavascript().$exec($("a", "b", "c")).toString());
		assertEquals("d.e.f;", new HwJavascript().$exec($("d", "e", "f")).toString());
	}
	
	@Test
	public void testIf3Times() {
		$if($eq($var("a"), $val(1)), () -> {
			$if($eq($var("b"), $val(2)), () -> {
				$if($eq($var("c"), $val(3)), () -> {
					$def("d", $val(4));
				});
			});
		});
		eq("if (a == 1) {\n" + "\tif (b == 2) {\n" + "\t\tif (c == 3) {\n" + "\t\t\tvar d = 4;\n" + "\t\t}\n" + "\t}\n" + "}");
	}
	
	protected void eq(String code) {
		assertEquals(code, $eof().toString());
	}
	
	protected void eq(String code, HwJavascript js) {
		assertEquals(code, js.$eof().toString());
		js.$reset();
	}
	
}
