package com.plater.android.core.utils

import java.io.File
import java.text.DecimalFormat

/**
 * AndroidDimensionsGenerator
 *
 * A utility class to generate dimension (dimen) resource files for different Android screen densities:
 * - mdpi (1x baseline)
 * - hdpi (1.5x scaling)
 * - xhdpi (2x scaling)
 * - xxhdpi (3x scaling)
 * - xxxhdpi (4x scaling)
 *
 * This generates values from 0 to 500 in 1dp increments, properly scaled for each density.
 */
class GenerateDimens {

    // Define the density buckets and their scaling factors
    private val densities = mapOf(
        "mdpi" to 0.5,    // Baseline (1x)
        "hdpi" to 0.75,    // 1.5x scaling
        "xhdpi" to 1.0,   // 2x scaling
        "xxhdpi" to 1.5,  // 3x scaling
        "xxxhdpi" to 2.0  // 4x scaling
    )

    // Format for decimal values (2 decimal places)
    private var decimalFormat = DecimalFormat("0.0")

    // Max dimension value to generate
    private val maxDimensionValue = 500

    /**
     * Generates dimension files for all Android density buckets
     * @param outputDirPath The path where to save the generated files
     */
    fun generateAllDimensionFiles(outputDirPath: String = "android_dimensions") {
        // Create output directory
        val outputDir = File(outputDirPath)
        outputDir.mkdirs()

        // Generate base dimension file
        generateBaseDimensionFile(outputDir)

        // Generate dimension files for each density
        densities.forEach { (density, factor) ->
            generateDimensionFile(outputDir, density, factor)
        }

        // Print summary
        printSummary(outputDir)
    }

    /**
     * Generates the baseline dimension file (values/dimens.xml)
     */
    private fun generateBaseDimensionFile(outputDir: File) {
        val baseResourceDir = File(outputDir, "values")
        baseResourceDir.mkdirs()

        val baseFile = File(baseResourceDir, "dimens.xml")
        baseFile.printWriter().use { writer ->
            writer.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
            writer.println("<resources>")

            for (i in 0..maxDimensionValue) {
                writer.println("    <dimen name=\"size_$i\">${i * 0.5}dp</dimen>")
            }

            writer.println("</resources>")
        }

        println("Generated ${baseFile.absolutePath} (baseline values)")
    }

    /**
     * Generates a dimension file for a specific density
     * @param outputDir The directory where to save the generated files
     * @param densityName The name of the density bucket (e.g., "hdpi")
     * @param scaleFactor The scaling factor for the density
     */
    private fun generateDimensionFile(outputDir: File, densityName: String, scaleFactor: Double) {
        // Create the resource directory
        val resourceDir = File(outputDir, "values-$densityName")
        resourceDir.mkdirs()

        // Create the dimens.xml file
        val dimensFile = File(resourceDir, "dimens.xml")
        dimensFile.printWriter().use { writer ->
            // Write XML header
            writer.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
            writer.println("<resources>")

            // Generate each dimension from 0 to 500
            for (i in 0..maxDimensionValue) {
                // Calculate the scaled value for this density
                val scaledValue = i * scaleFactor
                val formattedValue = decimalFormat.format(scaledValue).removeSuffix(".0")

                if (formattedValue.contains(".0")) {
                    formattedValue.replace(".0", "", true)
                }
                // For px values (scaled according to density)
                writer.println("    <dimen name=\"size_$i\">${formattedValue}dp</dimen>")
            }

            // Close the XML root element
            writer.println("</resources>")
        }

        println("Generated ${dimensFile.absolutePath} with ${maxDimensionValue + 1} values")
    }

    /**
     * Prints a summary of the generated files
     */
    private fun printSummary(outputDir: File) {
        println("\nAll dimension files have been generated in '${outputDir.absolutePath}'.")
        println("The files follow Android's density scaling guidelines:")
        densities.forEach { (density, factor) ->
            println("- $density: ${factor}x scaling factor")
        }
        println("\nEach file contains dimension resources from dp_0/px_0 to dp_${maxDimensionValue}/px_${maxDimensionValue}.")
    }
}

/**
 * Main function to execute the dimension generator
 */
fun main() {
    println("Android Dimensions Generator")
    println("============================")

    val generator = GenerateDimens()
    generator.generateAllDimensionFiles()

    println("\nDone! You can now copy these files to your Android project's 'res' directory.")
}