# How to use the relocation feature?
The library also provides a utility to relocate existing files to another directories in the system.

```java
void main(String[] args) {
  final var fileRelocationStatus = FilesRelocationUtils.relocateTo(
      /* We assume that the file is on the root directory. */ "test.jar",
      /* The file will be relocated to the given directory. */ "C:\\Users\\User\\Downloads\\test.jar",
      /* We indicate that any same file existing in the directory must be replaced. */ true,
      /* We indicate that the file on the previous directory must be deleted. */ true);
  // This method return a boolean state, so you can check if the file was relocated correctly
  // to the given directory, e.g.
  if (fileRelocationStatus) {
    System.out.println("The file was relocated correctly in the new directory.");
  }
}
```
