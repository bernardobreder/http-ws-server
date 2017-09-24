package httpws.nio.mock;

public class ServerChannelFixture {

	protected final ServerChannelMock mock;

	public ServerChannelFixture() {
		this(new ServerChannelMock());
	}

	public ServerChannelFixture(ServerChannelMock mock) {
		super();
		this.mock = mock;
	}

	/**
	 * Retorna o mock
	 *
	 * @return mock
	 */
	public ServerChannelMock target() {
		return mock;
	}

}
