package searchengine.config;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class LemmaConfiguration {

    /**
     * @return объект с русской морфологией
     * @throws IOException (если файл со словарем не найден)
     *                     <p>
     *                     Аннотация @Bean означает, что данный метод используется,
     *                     когда спрингу надо внедрить в другой класс спринга зависимость
     *                     типа LuceneMorphology</p>
     *                     <p>
     *                     С помощью данного подхода, можно внедрять в Spring классы не
     *                     входящие в контекст спринга, классы из библиотек или классы
     *                     содержащие бизнес логику
     *                     </p>
     */
    @Bean
    public LuceneMorphology luceneMorphology() throws IOException {
        return new RussianLuceneMorphology();
    }
}
