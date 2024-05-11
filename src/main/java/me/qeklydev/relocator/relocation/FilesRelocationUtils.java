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
package me.qeklydev.relocator.relocation;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.jetbrains.annotations.NotNull;

/**
 * This class is used to provide utilities for relocation of files
 * to specific directories.
 *
 * @since 0.0.1
 */
public final class FilesRelocationUtils {
  /**
   * This method will relocate the specified file from their previous directory,
   * to the new directory specified, and if there's the same file in the targeted
   * directory, we will replace it if user has preferred.
   *
   * @param previousDirectory the current directory where is the file to relocate.
   * @param nextDirectory the new directory where the file will be relocated.
   * @param replaceExistingFile indicates if the file must be replaced on the new
   *                            directory.
   * @return The boolean state for this operation, {@code true} if the relocation
   * was successful. Otherwise {@code false}.
   * @since 0.0.1
   */
  public static boolean relocateTo(final @NotNull String previousDirectory, final @NotNull String nextDirectory,
                                   final boolean replaceExistingFile) {
    // First at all, we need to get the Path reference for the given directories
    // to could handle their relocation.
    final var previousDirectoryAsPath = Paths.get(previousDirectory);
    final var nextDirectoryAsPath = Paths.get(nextDirectory);
    // We use a try/catch block to perform the relocation operation for the file,
    // We will return false for this operation, if any exception is catch during
    // Files#move(...) execution.
    try {
      // If the user has preferred replace the existing file in the directory with the current
      // file, we indicate this using [REPLACE_EXISTING] setting for the method, of this way
      // always that there's the same file in the targeted directory, it will be replaced with
      // the new.
      if (replaceExistingFile) {
        Files.move(previousDirectoryAsPath, nextDirectoryAsPath, StandardCopyOption.REPLACE_EXISTING);
      } else {
        Files.move(previousDirectoryAsPath, nextDirectoryAsPath);
      }
      // If any exception is triggered during these operations, then we can confirm that the
      // relocation was successful.
      return true;
    } catch (final Exception exception) {
      return false;
    }
  }
}
