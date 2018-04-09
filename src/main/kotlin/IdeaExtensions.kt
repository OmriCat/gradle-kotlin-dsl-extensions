import org.gradle.plugins.ide.idea.model.IdeaModule

inline var IdeaModule.downloadSources: Boolean
  get() = isDownloadSources
  set(value) {
    isDownloadSources = value
  }

inline var IdeaModule.downloadJavadoc: Boolean
  get() = isDownloadSources
  set(value) {
    isDownloadSources = value
  }
