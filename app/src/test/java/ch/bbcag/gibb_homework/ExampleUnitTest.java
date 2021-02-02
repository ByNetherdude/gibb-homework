package ch.bbcag.gibb_homework;

import org.junit.Test;

import ch.bbcag.gibb_homework.model.Module;
import ch.bbcag.gibb_homework.model.Task;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testModuleObjectConstructor(){
        Module module = new Module();
        module.setId(2);
        module.setActive(true);
        module.setNumber("153");
        module.setTitle("Datenmodelle entwickeln");
        module.setColor("#8DB4E2");

        assertEquals(true, module.isActive());
    }

    @Test
    public void testTaskObjectConstructor(){
        Task task = new Task();
        task.setTitle("Datenmodelle Entwickeln");
        task.setDescription("test");
        task.setModuleId(2);
        task.setDueDate("1.2.2022");

        assertEquals("Datenmodelle Entwickeln", task.getTitle());
    }
}