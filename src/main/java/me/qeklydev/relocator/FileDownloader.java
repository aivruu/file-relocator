/*
 * MIT License
 * Copyright (c) 2024 Qekly - file-relocator (https://github.com/aivruu/file-relocator)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package me.qeklydev.relocator;

import org.jetbrains.annotations.NotNull;
import java.net.http.HttpClient;

/**
 * This class is used to perform files download since the
 * given url's.
 *
 * @since 0.0.1
 */
public final class FileDownloader {
  private final HttpClient httpClient;
  private final String provider;
  private final String destinationDirectory;
  private final boolean mustBeReplaced;

  private FileDownloader(final @NotNull HttpClient httpClient, final @NotNull String provider,
                         final @NotNull String destinationDirectory, final boolean mustBeReplaced) {
    this.httpClient = httpClient;
    this.provider = provider;
    this.destinationDirectory = destinationDirectory;
    this.mustBeReplaced = mustBeReplaced;
  }

  /**
   * Creates a new builder for the file downloader object.
   *
   * @return A new {@link Builder}.
   * @since 0.0.1
   */
  public static @NotNull Builder builder() {
    return new Builder();
  }

  /**
   * Downloads the file since the given URL to the specified directory
   * for download destination.
   *
   * @param sync indicates if the download process must be processed
   *             over the main thread, or async way.
   * @since 0.0.1
   */
  public void downloadFile(final boolean sync) {
    // First at all, we need to create a new request for the given URL, and
    // then extract data for the file download process.

  }

  /**
   * This class is used to build instances of the file downloader.
   *
   * @since 0.0.1
   */
  public static class Builder {
    private HttpClient httpClient;
    private String url;
    private String destinationDirectory;
    private boolean mustBeReplaced;

    /**
     * Defines to the builder the http-client instance that will
     * be used to perform the url request.
     *
     * @param httpClient the http-client instance.
     * @return The current builder instance.
     * @since 0.0.1
     */
    public @NotNull Builder client(final @NotNull HttpClient httpClient) {
      this.httpClient = httpClient;
      return this;
    }

    /**
     * Defines the URL necessary to create an HTTP-request and download
     * the needed file.
     *
     * @param url the file url.
     * @return The current builder instance.
     * @since 0.0.1
     */
    public @NotNull Builder url(final @NotNull String url) {
      this.url = url;
      return this;
    }

    /**
     * Defines to the builder the destination directory for the
     * downloaded file.
     *
     * @param destinationDirectory the destination location where
     *                             the file will be downloaded.
     * @return The current builder instance.
     * @since 0.0.1
     */
    public @NotNull Builder destination(final @NotNull String destinationDirectory) {
      this.destinationDirectory = destinationDirectory;
      return this;
    }

    /**
     * Defines to the builder if the file must be replaced on the
     * destination directory if already there's a file with the
     * same name and extension.
     *
     * @param mustBeReplaced indicates if the current file must
     *                       override.
     * @return The current builder instance.
     * @since 0.0.1
     */
    public @NotNull Builder replaceExisting(final boolean mustBeReplaced) {
      this.mustBeReplaced = mustBeReplaced;
      return this;
    }

    /**
     * Creates a new file downloader object using the information given to
     * the builder, this method could throw an exception if some parameter
     * was not defined during the builder configuration.
     *
     * @return The built {@link FileDownloader}.
     * @since 0.0.1
     */
    public @NotNull FileDownloader build() {
      // We check if any [HttpClient] has been defined for the builder, this is
      // necessary to perform request to the file URL, and start download for
      // the file.
      if (this.httpClient == null) {
        throw new IllegalStateException("The FileDownloader.Builder instance requires a HttpClient to perform file download.");
      }
      // If the file url, or destination directory, or both are not defined for the
      // builder, we throw an exception indicating the details for this.
      if (this.url == null || this.destinationDirectory == null) {
        throw new IllegalStateException("The file URL, or destination directory have not been defined on FileDownloader.Builder.");
      }
      // If we have all the required information for the constructor, we can create a new
      // instance of [FileDownloader], this will be used to perform file download.
      return new FileDownloader(this.httpClient, this.url, this.destinationDirectory, this.mustBeReplaced);
    }
  }
}
