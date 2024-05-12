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

import me.qeklydev.relocator.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.CompletableFuture;

/**
 * This class is used to perform files download since the
 * given url's.
 *
 * @since 0.0.1
 */
public record FileDownloader(@NotNull String fileName, @NotNull String provider, boolean mustBeReplaced) {
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
   * @return The boolean state for this operation, {@code true} if the
   * file was downloaded and relocated correctly. Otherwise {@code false}.
   * @see IOUtils#writeSync(String, String)
   * @since 0.0.1
   */
  public boolean downloadFileSync() {
    final var fileBytesWritingStatus = IOUtils.writeSync(this.fileName, this.provider);
    // If the bytes read amount is zero, indicates that something went wrong
    // during writing process and the file could not be downloaded correctly,
    // so we return a [ false ] boolean state for this operation.
    return fileBytesWritingStatus > IOUtils.SINGLE_RETURN_VALUE;
  }

  /**
   * Downloads the file of async way, since the given URL to the specified directory
   * for download destination.
   *
   * @return The {@link CompletableFuture} for this operation with the final
   * supply for the internal download operation, this supply is a boolean state
   * that represents the final status for the async operation, check the
   * downloadFileSync description to understand internal method functions.
   * @see FileDownloader#downloadFileSync()
   * @since 0.0.1
   */
  public @NotNull CompletableFuture<@NotNull Boolean> downloadFileAsync() {
    // The executor for downloadFileSync(...) is the same, there's the difference
    // that this process is executed async way, so this will return a completable-future
    // with the supply, once the async operation has completed and final result was provided.
    return CompletableFuture.supplyAsync(this::downloadFileSync);
  }

  /**
   * This class is used to build instances of the file downloader.
   *
   * @since 0.0.1
   */
  public static class Builder {
    private String fileName;
    private String url;
    private boolean mustBeReplaced;

    /**
     * Defines to the builder the name that will receive the file
     * once have been downloaded.
     *
     * @param fileName the name for the downloaded file.
     * @return The current builder instance.
     * @since 0.0.1
     */
    public @NotNull Builder name(final @NotNull String fileName) {
      this.fileName = fileName;
      return this;
    }

    /**
     * Defines the url for download the file.
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
      // We check that the file-name, the file url, and the destination directory for the
      // downloaded file were defined correctly on the builder, other-wise, we throw an
      // exception indicating the details for the operation mishap.
      if (this.fileName == null || this.url == null) {
        throw new IllegalStateException("The file name, or file url have not been defined on FileDownloader.Builder.");
      }
      // If we have all the required information for the constructor, we can create a new
      // instance of [FileDownloader], this will be used to perform file download.
      return new FileDownloader(this.fileName, this.url, this.mustBeReplaced);
    }
  }
}
