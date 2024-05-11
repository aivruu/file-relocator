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
package me.qeklydev.relocator.io;

import java.io.FileOutputStream;
import java.net.URI;
import java.nio.channels.Channels;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;

/**
 * This class is used to give I/O utils for files.
 *
 * @since 0.0.1
 */
public final class IOUtils {
  /**
   * The single value that will be returned in case that writing
   * has suffered a mishap.
   *
   * @since 0.0.1
   */
  public static final long SINGLE_RETURN_VALUE = 0L;

  /**
   * This method is used when we need to download files with a lower weight
   * and that doesn't require much processing, this method will return the amount
   * of bytes read since the given provider.
   *
   * @param fileNameWithExtension the name that will receive the file.
   * @param provider the provider (URL) used to read the file information.
   * @return The amount of bytes read for the downloaded file, will return {@code 0}
   * if the file was not downloaded successfully, or an exception was triggered.
   * @see IOUtils#SINGLE_RETURN_VALUE
   * @since 0.0.1
   */
  public static long writeSync(final @NotNull String fileNameWithExtension, final @NotNull String provider) {
    // Using the given url as string by the user, we try to parse it and use it to create
    // a new [URI] object (Java 20 has deprecated URL constructors, now for get a URL reference
    // we need to invoke URI#toURL() method).
    //
    // If the given URL is not valid, the method will throw an exception indicating a bad
    // syntax given for the URL.
    final var uri = URI.create(provider);
    // If the url is valid and the URI object was created, we can proceed with the process
    // for the file download. We obtain a URL object since the URI object created, and we
    // create a [FileOutputStream] using the given file name with his full extension.
    try (final var readableByteChannel = Channels.newChannel(uri.toURL().openStream());
         final var fileOutputStream = new FileOutputStream(fileNameWithExtension)) {
      // If the readable byte channel for bytes information transfer is not opened,
      // we return the [SINGLE_RETURN_VALUE] that aims to a value -> 0L.
      //
      // Otherwise, we can proceed with the bytes information transfer for the specified
      // file, and be downloaded.
      if (!readableByteChannel.isOpen()) {
        return SINGLE_RETURN_VALUE;
      }
      // We start the bytes information transfer to the channel of the [FileOutputStream] object,
      // we indicate the value of [SINGLE_RETURN_VALUE] as initial position for start the information
      // transfer between the channels.
      return fileOutputStream.getChannel().transferFrom(
          readableByteChannel, /* The initial position taken for start bytes transfers. */ SINGLE_RETURN_VALUE, Long.MAX_VALUE);
    } catch (final Exception exception) {
      return SINGLE_RETURN_VALUE;
    }
  }

  /**
   * This method meets the same function that writeSync(...), with the only
   * difference that the writing is performed async way.
   *
   * @param fileNameWithExtension the name that will receive the file.
   * @param provider the provider (URL) used to read the file information.
   * @return The amount of bytes read for the downloaded file, will return {@code 0}
   * if the file was not downloaded successfully, or an exception was triggered.
   * @see IOUtils#SINGLE_RETURN_VALUE
   * @see IOUtils#writeSync(String, String)
   * @since 0.0.1
   */
  public static @NotNull CompletableFuture<@NotNull Long> writeAsync(final @NotNull String fileNameWithExtension, final @NotNull String provider) {
    // The execution for writeSync(...) is the same, there's the case that we will process the
    // operation of async way.
    return CompletableFuture.supplyAsync(() -> writeSync(fileNameWithExtension, provider));
  }
}
