package httpws.hws;

import java.util.List;

import httpws.component.HWButton;
import httpws.component.HWComponent;
import httpws.component.HWFrame;
import httpws.nio.HwBuffer;
import httpws.nio.ws.HwIWsServerModel;
import httpws.nio.ws.HwWsServer;

/**
 * Aplicativo do HttpWebSocket Server
 *
 * @author Bernardo Breder
 */
public class HwWsServerModel implements HwIWsServerModel {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object connected(HwWsServer server, String id) {
		return new HWBrowser();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void request(HwWsServer server, String id, Object context, HwBuffer reqBuffer) {
		IBrowser browser = (IBrowser) context;
		while (reqBuffer.hasRemaining()) {
			HwWsAppOpcode opcode = HwWsAppOpcode.values()[reqBuffer.getUInt8()];
			switch (opcode) {
				case OPEN: {
					String path = reqBuffer.getString();
					if (path.length() == 0) {
						path = "#";
					}
					HWFrame frame = new DesktopFrame();
					frame.setBrowser(browser);
					browser.appendBody(frame.getCode());
					break;
				}
				case CALL: {
					int componentId = reqBuffer.getUInt32();
					HWComponent component = browser.getComponent(componentId);
					if (component instanceof HWButton) {
						HWButton button = (HWButton) component;
						button.getOkAction().action();
					}
					break;
				}
			}
		}
		List<HwBuffer> respBuffers = browser.consumeBuffers();
		if (!respBuffers.isEmpty()) {
			for (HwBuffer buffer : respBuffers) {
				buffer.flip();
				server.send(id, new HwBuffer(buffer.remaining() + 64).putWsBytes(buffer).flip());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disconnected(HwWsServer server, String id) {
	}

}
