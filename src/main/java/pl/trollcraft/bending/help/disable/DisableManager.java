package pl.trollcraft.bending.help.disable;

import java.util.HashSet;

public class DisableManager {

    private static HashSet<DisableTask> tasks = new HashSet<>();

    public static void registerTask(DisableTask task) { tasks.add(task); }

    public static void executeTasks() {
        tasks.forEach(t -> t.execute());
    }

}
