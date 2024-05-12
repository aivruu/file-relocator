# How to use the downloader feature
The library offers the possibility of download files since URL's, any URL'S that aims to a 
downloadable file can be used, this can be done of sync or async way, depending on your necessities.

```java
void main(String[] args) {
  final var fileDownloader = FileDownloader.builder()
      .name("test.jar") // The name that will receive the downloaded file.
      .url("https://...") // The URL since where the file will be downloaded.
      .replaceExisting(true) // Indicates that any existing coincidence for this file will be overwritten.
      .build(); // Creates a new file downloader object.
  // This class offers two methods to perform the file download, one sync, and another async
  // Their usage depending on your necessities, the available resources on the machine, or
  // the size of the file to download (but, all we know that anyone is going to download a 50 GB file using this, :8).
  final var syncDownloadStatus = fileDownloader.downloadFileSync(); // The file will be downloaded using the current thread.
  final var asyncDownloadStatus = fileDownloader.downloadFileAsync(); // The file will be downloaded using another thread for the process.
  // Both methods return boolean states, so you can check if the file was downloaded correctly.
  if (syncDownloadStatus) {
    System.out.println("The file was downloaded successfully using a single thread!");
  }
}
```
