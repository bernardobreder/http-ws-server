package httpws.builder.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import httpws.builder.css.HWStyleCode;
import httpws.builder.css.HwCss;
import httpws.builder.js.HWJavascriptCode;
import httpws.builder.js.HwJavascript;
import httpws.util.Charset;

public class HWHtmlPage {
	
	private ByteArrayOutputStream out;
	
	private HSHeadPage head = new HSHeadPage();
	
	private HSBodyPage body = new HSBodyPage();
	
	private HSTagAttr attr = new HSTagAttr();
	
	public HWHtmlPage() throws IOException {
		out = new ByteArrayOutputStream(1024);
		out.write(Charset.ascii("<!DOCTYPE html>\r\n"));
		out.write(Charset.ascii("<html>"));
	}
	
	public byte[] closeHtml() throws IOException {
		out.write(Charset.ascii("</html>"));
		return out.toByteArray();
	}
	
	public HSHeadPage head() throws IOException {
		out.write(Charset.ascii("<head>"));
		out.write(Charset.ascii("<meta charset='utf-8'>"));
		out.write(Charset.ascii("<meta name='viewport' content='width=device-width, initial-scale=1'>"));
		out.write(Charset.ascii("<meta http-equiv='x-ua-compatible' content='ie=edge'>"));
		out.write(Charset.ascii("<meta name='viewport' content='width=device-width, initial-scale=1'>"));
		return head;
	}
	
	public HSBodyPage body() throws IOException {
		out.write(Charset.ascii("<body>"));
		return body;
	}
	
	public class HSHeadPage {
		
		public HSHeadPage scriptSrc(String src) throws IOException {
			out.write(Charset.ascii("<script src='" + src + "'></script>"));
			return this;
		}
		
		public HSHeadPage scriptString(String code) throws IOException {
			out.write(Charset.ascii("<script>\r\n"));
			out.write(Charset.utf8(code));
			out.write(Charset.ascii("</script>\r\n"));
			return this;
		}
		
		public HSHeadPage scriptString(HwJavascript js) throws IOException {
			out.write(Charset.ascii("<script>\r\n"));
			out.write(Charset.utf8(js.toString()));
			out.write(Charset.ascii("</script>\r\n"));
			return this;
		}
		
		public HSHeadPage scriptCode(HWJavascriptCode js) throws IOException {
			out.write(Charset.ascii("<script>\r\n"));
			out.write(Charset.utf8(js.toString()));
			out.write(Charset.ascii("</script>\r\n"));
			return this;
		}
		
		public HSBodyPage close() throws IOException {
			out.write(Charset.ascii("</head>"));
			return HWHtmlPage.this.body;
		}
		
		public HSHeadPage cssCode(HWStyleCode css) throws IOException {
			out.write(Charset.ascii("<style>\r\n"));
			out.write(Charset.utf8(css.toString()));
			out.write(Charset.ascii("</style>\r\n"));
			return this;
		}
		
		public HSHeadPage cssCode(HwCss css) throws IOException {
			out.write(Charset.ascii("<style>\r\n"));
			out.write(Charset.utf8(css.toString()));
			out.write(Charset.ascii("</style>\r\n"));
			return this;
		}
		
	}
	
	public class HSBodyPage {
		
		public HWHtmlPage closeBody() throws IOException {
			out.write(Charset.ascii("</body>"));
			return HWHtmlPage.this;
		}
		
		public HSTagAttr h2() throws IOException {
			return attr.open("h2");
		}
		
	}
	
	public class HSTagAttr {
		
		protected String tag;
		
		protected HSTagAttr open(String tag) throws IOException {
			this.tag = tag;
			out.write(Charset.ascii("<" + tag));
			return this;
		}
		
		public HSTagAttr addClass(String... names) throws IOException {
			out.write(Charset.ascii(" class='"));
			for (int n = 0; n < names.length; n++) {
				out.write(Charset.ascii(names[n]));
				if (n != names.length - 1) {
					out.write(' ');
				}
			}
			out.write('\'');
			return this;
		}
		
		public HSTagAttr id(String id) throws IOException {
			out.write(Charset.ascii(" id='"));
			out.write(Charset.utf8(id));
			out.write('\'');
			return this;
		}
		
		public HSBodyPage text(String text) throws IOException {
			out.write('>');
			out.write(Charset.utf8(text));
			out.write(Charset.ascii("</" + tag + ">"));
			return HWHtmlPage.this.body;
		}
		
		public HSBodyPage closeTag() throws IOException {
			out.write('>');
			out.write(Charset.ascii("</" + tag + ">"));
			return HWHtmlPage.this.body;
		}
		
	}
	
}
