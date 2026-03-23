package com.sprint.mission.service.file;

import com.sprint.mission.entity.Entity;
import com.sprint.mission.service.BaseService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class FileBaseService<T extends Entity<T>> implements BaseService<T> {
    private final Path saveDirectory;
    private final String extension;
    private final String ID_NOT_FOUND_MSG = "%s id not found, %s";

    public FileBaseService(String extension) {
        this.extension = extension;
        this.saveDirectory = Path.of(System.getProperty("user.dir"), "repository", extension);
        try {
            Files.createDirectories(saveDirectory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void save(T entity) {
        Path path = saveDirectory.resolve(getFileName(entity.getId()));
        try (
                FileOutputStream fos = new FileOutputStream(path.toFile());
                ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(entity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private T load(UUID id) {
        Path path = saveDirectory.resolve(getFileName(id));
        try (
                FileInputStream fis = new FileInputStream(path.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T get(UUID id) {
        if (hasId(id)) {
            return load(id);
        }
        throw new IllegalArgumentException(ID_NOT_FOUND_MSG.formatted(extension, id));
    }

    @Override
    public <R extends Collection<UUID>> Map<UUID, T> getAll(R ids) {
        return ids.stream().map(this::get)
                .collect(Collectors.toMap(Entity::getId, entity -> entity));
    }

    @Override
    public void update(UUID id, String newValue) {
        T entity = get(id);
        entity.update(newValue);
        save(entity);
    }

    @Override
    public void delete(UUID id) {
        Path path = saveDirectory.resolve(getFileName(id));
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFileName(UUID id) {
        return String.join(".", id.toString(), extension);
    }

    @Override
    public boolean hasId(UUID id) {
        try (Stream<Path> fileList = Files.list(saveDirectory);) {
            return fileList
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .map(fileName -> fileName.replace("." + extension, "")) // remove extension
                    .anyMatch(entityId -> entityId.equals(id.toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
