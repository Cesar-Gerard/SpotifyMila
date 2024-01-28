package Interface;

import java.util.List;

import modelApi.AlbumApi.Image;
import modelApi.TopAlbums.Artist;

public interface AlbumInterface {

    String getName();
    String getArtist();

    Artist getArtist_();
    List<Image> getImage();

}
