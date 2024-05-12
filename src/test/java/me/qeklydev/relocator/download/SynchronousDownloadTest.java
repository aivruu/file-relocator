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
package me.qeklydev.relocator.download;

import me.qeklydev.relocator.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SynchronousDownloadTest {
  @Test
  @DisplayName("sync-download-test")
  void test() {
    final var start = System.currentTimeMillis();
    final var fileDownloadBytesAmount = IOUtils.writeSync(
        "ReleaseDownloader-Sync.jar",
        "https://github.com/aivruu/release-downloader/releases/download/1.3.4/release-downloader-1.3.4.jar");
    // Indicates that the amount of read bytes for the downloaded file must be higher
    // than the value for [SINGLE_RETURN_VALUE] which is 0L.
    Assertions.assertTrue(fileDownloadBytesAmount > IOUtils.SINGLE_RETURN_VALUE);
    System.out.println("Synchronous Download Milliseconds Elapsed: " + (System.currentTimeMillis() - start));
  }
}
