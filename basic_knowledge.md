## TODO
1. learn to use record
eg:
```
static record Options(@Option("-l")boolean logging, @Option("-p")int port, @Option("-d")String directroy){}
```
2.  How to write annatation ?

```
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface Option {
}
```

