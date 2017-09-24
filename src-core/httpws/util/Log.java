package httpws.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe que realiza o log
 *
 * @author Bernardo Breder
 */
public class Log {
	
	/** Formatador de Data */
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
	
	/** Level do log */
	private static Level level;
	
	/**
	 * Inicializa o log
	 *
	 * @param level
	 */
	public static void init(Level level) {
		Log.level = level;
		info("Log started.");
	}
	
	/**
	 * Realiza o log para info
	 *
	 * @param format
	 * @param objects
	 */
	public static void info(String format, Object... objects) {
		if (level.ordinal() > Level.INFO.ordinal()) { return; }
		if (objects.length > 0) {
			format = String.format(format, objects);
		}
		System.out.println("[" + DATE_FORMAT.format(new Date()) + "][info]: " + format);
	}
	
	/**
	 * Realiza o log para error
	 *
	 * @param format
	 * @param objects
	 */
	public static void error(String format, Object... objects) {
		if (level.ordinal() > Level.DEFAULT.ordinal()) { return; }
		if (objects.length > 0) {
			format = String.format(format, objects);
		}
		System.err.println("[" + DATE_FORMAT.format(new Date()) + "][error]: " + format);
	}
	
	/**
	 * Realiza o log para opcode
	 *
	 * @param format
	 * @param objects
	 */
	public static void opcode(String format, Object... objects) {
		if (level.ordinal() > Level.DEBUG.ordinal()) { return; }
		if (objects.length > 0) {
			format = String.format(format, objects);
		}
		System.out.println("[" + DATE_FORMAT.format(new Date()) + "][opcode]: " + format);
	}
	
	/**
	 * Nível de Log
	 *
	 * @author Bernardo Breder
	 */
	public static enum Level {
		
		/**
		 * Debug
		 */
		DEBUG, /**
				 * Info
				 */
		INFO, /**
				 * Padrão
				 */
		DEFAULT;
		
	}
	
}
