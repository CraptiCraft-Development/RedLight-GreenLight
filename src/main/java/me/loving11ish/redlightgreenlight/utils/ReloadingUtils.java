package me.loving11ish.redlightgreenlight.utils;

public class ReloadingUtils {

    // Below method originally created by ProtocolLib!
    // It is a good way of checking this so there is no point recreating the wheel!
    // @ProtocolLib devs: Please don't crucify me for using this!!

    public static boolean isCurrentlyReloading() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        for (StackTraceElement element : stackTrace) {
            String clazz = element.getClassName();

            if (clazz.startsWith("org.bukkit.craftbukkit.")
                    && clazz.endsWith(".CraftServer")
                    && element.getMethodName().equals("reload")) {
                return true;
            }
        }
        return false;
    }
}
