package com.upload.file;

import org.springframework.content.commons.repository.ContentStore;

public interface FileContentRepository extends ContentStore<FileObject, String> {
}
